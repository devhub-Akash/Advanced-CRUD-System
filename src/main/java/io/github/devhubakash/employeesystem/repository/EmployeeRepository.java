package io.github.devhubakash.employeesystem.repository;

import io.github.devhubakash.employeesystem.dto.EmployeeResponseDTO;
import io.github.devhubakash.employeesystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    Optional<Employee> findByEmpIdAndDeletedIsFalse(String empId);
    List<Employee> findByDeletedIsFalse();
}
