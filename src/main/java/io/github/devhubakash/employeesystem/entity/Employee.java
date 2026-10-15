package io.github.devhubakash.employeesystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "demo_employee")
public class Employee {

    @Id
    @Column(unique = true, nullable = false)
    private String empId;

    @Column(nullable = false)
    private String empName;

    @Column(nullable = false)
    private String deptName;

    @Column(nullable = false)
    private BigDecimal salary;

    private Boolean deleted = false;
}
