package io.github.devhubakash.employeesystem.controller;

import io.github.devhubakash.employeesystem.dto.EmployeeRequestDTO;
import io.github.devhubakash.employeesystem.dto.EmployeeResponseDTO;
import io.github.devhubakash.employeesystem.entity.Employee;
import io.github.devhubakash.employeesystem.exception.ResourceNotFoundException;
import io.github.devhubakash.employeesystem.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    // CREATE
    @PostMapping("create")
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        EmployeeResponseDTO created = employeeService.createEmployee(employeeRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // READ ALL
    @GetMapping("getall")
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees(){
        List<EmployeeResponseDTO> employeeList = employeeService.getAllEmployees();
        return ResponseEntity.ok().body(employeeList);
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
