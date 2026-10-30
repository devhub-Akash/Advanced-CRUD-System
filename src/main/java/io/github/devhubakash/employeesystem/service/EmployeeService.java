package io.github.devhubakash.employeesystem.service;

import io.github.devhubakash.employeesystem.dto.EmployeeFilterDTO;
import io.github.devhubakash.employeesystem.dto.EmployeeRequestDTO;
import io.github.devhubakash.employeesystem.dto.EmployeeResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface EmployeeService {

    EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO);

    EmployeeResponseDTO getEmployeeById(String empId);

    Page<EmployeeResponseDTO> getAllEmployees(Pageable pageable);

    EmployeeResponseDTO updateEmployee(String empId, EmployeeRequestDTO employeeRequestDTO);

    void softDeleteEmployee(String empId);

    void permanentDeleteEmployee(String empId);

    Page<EmployeeResponseDTO> searchEmployees(EmployeeFilterDTO filter, Pageable pageable);
}
