package io.github.devhubakash.employeesystem.mapper;

import io.github.devhubakash.employeesystem.dto.EmployeeRequestDTO;
import io.github.devhubakash.employeesystem.dto.EmployeeResponseDTO;
import io.github.devhubakash.employeesystem.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public Employee toEntity(EmployeeRequestDTO employeeRequestDTO){
        Employee employee = new Employee();

        employee.setEmpId(employeeRequestDTO.getEmpId());
        employee.setEmpName(employeeRequestDTO.getEmpName());
        employee.setDeptName(employeeRequestDTO.getDeptName());
        employee.setSalary(employeeRequestDTO.getSalary());

        employee.setDeleted(false);
        return employee;
    }

    public EmployeeResponseDTO toResponse(Employee  employee){
        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();

        employeeResponseDTO.setEmpId(employee.getEmpId());
        employeeResponseDTO.setEmpName(employee.getEmpName());
        employeeResponseDTO.setDeptName(employee.getDeptName());
        employeeResponseDTO.setSalary(employee.getSalary());

        return employeeResponseDTO;
    }

    public void updateEntity(Employee employee, EmployeeRequestDTO employeeRequestDTO){
        employee.setEmpName(employeeRequestDTO.getEmpName());
        employee.setDeptName(employeeRequestDTO.getDeptName());
        employee.setSalary(employeeRequestDTO.getSalary());
    }
}
