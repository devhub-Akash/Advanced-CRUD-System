package io.github.devhubakash.employeesystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EmployeeRequestDTO {

    @NotBlank(message = "Employee id is required")
    private String empId;

    @NotBlank(message = "Employee name is required")
    private String empName;

    @NotBlank(message = "Department name is required")
    private String deptName;

    @NotNull(message = "Salary is required")
    @PositiveOrZero(message = "Salary must be positive or equal to zero")
    private BigDecimal salary;

}
