package io.github.devhubakash.employeesystem.service;

import io.github.devhubakash.employeesystem.dto.EmployeeFilterDTO;
import io.github.devhubakash.employeesystem.dto.EmployeeRequestDTO;
import io.github.devhubakash.employeesystem.dto.EmployeeResponseDTO;
import io.github.devhubakash.employeesystem.entity.Employee;
import io.github.devhubakash.employeesystem.exception.BadRequestException;
import io.github.devhubakash.employeesystem.exception.ResourceNotFoundException;
import io.github.devhubakash.employeesystem.mapper.EmployeeMapper;
import io.github.devhubakash.employeesystem.repository.EmployeeRepository;
import io.github.devhubakash.employeesystem.specification.EmployeeSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;


@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO) {

        if (employeeRepository.existsById(employeeRequestDTO.getEmpId())) {
            throw new BadRequestException("Employee ID already exists: " + employeeRequestDTO.getEmpId());
        }

        Employee emp = employeeMapper.toEntity(employeeRequestDTO);
        Employee saved = employeeRepository.save(emp);
        return employeeMapper.toResponse(saved);
    }


    @Override
    public Page<EmployeeResponseDTO> getAllEmployees(Pageable pageable) {
        log.debug("Fetching employees - page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());

        return employeeRepository.findByDeletedIsFalse(pageable)
                .map(employeeMapper::toResponse);
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(String empId) {
        log.debug("Fetching employee: {}", empId);
        Employee employee = employeeRepository.findByEmpIdAndDeletedIsFalse(empId)
                .orElseThrow(()-> {
                    log.warn("Employee not found: {}", empId);
                    return new ResourceNotFoundException("Employee not found: " + empId);
                });

        return employeeMapper.toResponse(employee);
    }

    @Transactional
    @Override
    public EmployeeResponseDTO updateEmployee(String empId, EmployeeRequestDTO employeeRequestDTO) {
        log.info("update request for empId: {}",empId);

        Employee employee = employeeRepository.findByEmpIdAndDeletedIsFalse(empId)
                .orElseThrow(()-> {
                    log.warn("update failed - employee not found or deleted: {}", empId);
                    return new ResourceNotFoundException("Employee not found");
                });

        employeeMapper.updateEntity(employee, employeeRequestDTO);
        Employee updated = employeeRepository.save(employee);

        log.info("Employee updated successfully: {}", empId);
        return employeeMapper.toResponse(updated);
    }

    @Transactional
    @Override
    public void softDeleteEmployee(String empId){
        log.info("Soft delete request for empId: {}", empId);
        Employee employeeToDelete = employeeRepository
                .findByEmpIdAndDeletedIsFalse(empId)
                .orElseThrow(() -> {
                    log.warn("Soft deletion failed - employee not found: {}", empId);
                   return new ResourceNotFoundException("Employee with id: " + empId + " not exists");
                });

        employeeToDelete.setDeleted(true);
        employeeRepository.save(employeeToDelete);

        log.info("Employee soft deleted successfully: {}", empId);
    }

    @Transactional
    @Override
    public void permanentDeleteEmployee(String empId) {
        log.warn("Permanent delete request for empId: {}", empId);

        Employee employeeToDelete = employeeRepository
                .findById(empId)
                .orElseThrow(() -> {
                    log.warn("Permanent deletion failed - employee not found: {}", empId);
                    return new ResourceNotFoundException("Employee with id " + empId + " not exist" );
                });

        employeeRepository.delete(employeeToDelete);
        log.warn("Employee PERMANENTLY deleted: {}", empId);
    }

    @Override
    public Page<EmployeeResponseDTO> searchEmployees(EmployeeFilterDTO filter, Pageable pageable) {
        log.debug("search request - filter: {}", filter);

        Specification<Employee> specs = EmployeeSpecification.notDeleted();

        if(filter.getEmpId() != null && !filter.getEmpId().isBlank()) {
            specs = specs.and(EmployeeSpecification.hasEmpId(filter.getEmpId().trim()));
        }

        if(filter.getEmpId() != null && !filter.getEmpName().isBlank()) {
            specs = specs.and(EmployeeSpecification.nameContains(filter.getEmpName().trim()));
        }

        if(filter.getDeptName() != null && !filter.getDeptName().isBlank()) {
            specs = specs.and(EmployeeSpecification.hasDept(filter.getDeptName().trim()));
        }

        if(filter.getMinSalary() != null) {
            specs = specs.and(EmployeeSpecification.salaryGreaterThan(filter.getMinSalary()));
        }

        if(filter.getMaxSalary() != null) {
            specs = specs.and(EmployeeSpecification.salaryLessThan(filter.getMaxSalary()));
        }

        Page<Employee> employees = employeeRepository.findAll(specs, pageable);
        log.info("Search result: {} employees found (total: {})", employees.getNumberOfElements(), employees.getTotalElements());

        return employees.map(employeeMapper::toResponse);
    }

}
