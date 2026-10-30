package io.github.devhubakash.employeesystem.controller;

import io.github.devhubakash.employeesystem.dto.EmployeeFilterDTO;
import io.github.devhubakash.employeesystem.dto.EmployeeRequestDTO;
import io.github.devhubakash.employeesystem.dto.EmployeeResponseDTO;
import io.github.devhubakash.employeesystem.entity.Employee;
import io.github.devhubakash.employeesystem.exception.ResourceNotFoundException;
import io.github.devhubakash.employeesystem.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.swing.*;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/api/employees")
public class EmployeeController {

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("empId", "empName", "deptName", "salary");

    private final EmployeeService employeeService;

     // CREATE
    @PostMapping("/create")
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        EmployeeResponseDTO created = employeeService.createEmployee(employeeRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // READ ALL
    @GetMapping("/getall")
    public ResponseEntity<Page<EmployeeResponseDTO>> getAllEmployees(

            @ParameterObject EmployeeFilterDTO employeeFilterDTO,

            @RequestParam(defaultValue = "1")
            @Min(value = 1, message = "pageNo must be >=1")int pageNo,

            @RequestParam(defaultValue = "5")
            @Min(value = 1, message = "pageSize must be >=1")
            @Max(value = 100, message = "pageSize must be <=100") int pageSize,

            @RequestParam(defaultValue = "empId") String sortBy,
            @RequestParam(defaultValue = "asc") String direction)
    {
        if(!ALLOWED_SORT_FIELDS.contains(sortBy)){
            throw new IllegalArgumentException("Invalid sortBy. Allowed values: " + ALLOWED_SORT_FIELDS);
        }

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNo -1, pageSize, sort);

        Page<EmployeeResponseDTO> employeePage = employeeService.searchEmployees(employeeFilterDTO, pageable);
        return ResponseEntity.ok().body(employeePage);
    }

    // READ ONE
    @GetMapping("/by-id")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@RequestParam String empId){
        EmployeeResponseDTO emp = employeeService.getEmployeeById(empId);
        return ResponseEntity.ok().body(emp);
    }

    // UPDATE
    @PutMapping("/update")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(@RequestParam String empId, @Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        EmployeeResponseDTO updated = employeeService.updateEmployee(empId, employeeRequestDTO);
        return ResponseEntity.ok().body(updated);
    }

    // DELETE PERMANENT
    @DeleteMapping("/delete")
    public ResponseEntity<Void> permanentDeleteEmployee(@RequestParam String empId){
        employeeService.permanentDeleteEmployee(empId);
        return ResponseEntity.noContent().build();
    }

    // DELETE TEMPORARY
    @PatchMapping("/soft-delete")
    public ResponseEntity<Void> softDeleteEmployee(@RequestParam String empId) {
        employeeService.softDeleteEmployee(empId);
        return ResponseEntity.noContent().build();
    }
}
