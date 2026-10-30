package io.github.devhubakash.employeesystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

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

    @Column(nullable = false)
    private Boolean deleted = false;

}
