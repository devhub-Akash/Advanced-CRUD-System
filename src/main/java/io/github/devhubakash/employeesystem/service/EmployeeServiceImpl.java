package io.github.devhubakash.employeesystem.service;

import io.github.devhubakash.employeesystem.dto.EmployeeRequestDTO;
import io.github.devhubakash.employeesystem.dto.EmployeeResponseDTO;
import io.github.devhubakash.employeesystem.entity.Employee;
import io.github.devhubakash.employeesystem.exception.BadRequestException;
import io.github.devhubakash.employeesystem.exception.ResourceNotFoundException;
import io.github.devhubakash.employeesystem.mapper.EmployeeMapper;
import io.github.devhubakash.employeesystem.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeRepository.findByDeletedIsFalse()
                .stream()
                .map(employeeMapper::toResponse)
                .toList();
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(String empId) {
        Employee employee = employeeRepository.findByEmpIdAndDeletedIsFalse(empId)
                .orElseThrow(()->new ResourceNotFoundException("Employee not found"));

        return employeeMapper.toResponse(employee);
    }

    @Override
    public EmployeeResponseDTO updateEmployee(String empId, EmployeeRequestDTO employeeRequestDTO) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(()-> new ResourceNotFoundException("Employee not found"));

        employeeMapper.updateEntity(employee, employeeRequestDTO);
        return employeeMapper.toResponse(employeeRepository.save(employee));
    }

    @Override
    public void softDeleteEmployee(String empId){
        Employee employeeToDelete = employeeRepository
                .findByEmpIdAndDeletedIsFalse(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id " + empId + " not exist" ));

        employeeToDelete.setDeleted(true);
        employeeRepository.save(employeeToDelete);
    }

    @Override
    public void permanentDeleteEmployee(String empId) {
        Employee employeeToDelete = employeeRepository
                .findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id " + empId + " not exist" ));

        employeeRepository.delete(employeeToDelete);
    }

}
