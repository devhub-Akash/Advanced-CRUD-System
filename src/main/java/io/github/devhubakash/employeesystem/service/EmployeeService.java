package io.github.devhubakash.employeesystem.service;

import io.github.devhubakash.employeesystem.dto.EmployeeRequestDTO;
import io.github.devhubakash.employeesystem.dto.EmployeeResponseDTO;

import java.util.List;

public interface EmployeeService {

    EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO);

    EmployeeResponseDTO getEmployeeById(String empId);

    List<EmployeeResponseDTO> getAllEmployees();

    EmployeeResponseDTO updateEmployee(String empId, EmployeeRequestDTO employeeRequestDTO);

    void softDeleteEmployee(String empId);

    void permanentDeleteEmployee(String empId);

}
