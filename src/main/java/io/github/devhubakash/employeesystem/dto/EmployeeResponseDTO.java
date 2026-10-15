package io.github.devhubakash.employeesystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EmployeeResponseDTO {

    private String empId;
    private String empName;
    private String deptName;
    private BigDecimal salary;

}
