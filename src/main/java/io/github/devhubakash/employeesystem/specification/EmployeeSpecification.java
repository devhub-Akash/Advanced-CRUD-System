package io.github.devhubakash.employeesystem.specification;

import io.github.devhubakash.employeesystem.entity.Employee;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;


public class EmployeeSpecification {

    public static Specification<Employee> notDeleted() {
        return (root, query, cb) ->
                cb.or(cb.isNull(root.get("deleted")), cb.isFalse(root.get("deleted")));
    }

    public static Specification<Employee>  hasEmpId(String empId) {
        return (root, query, cb) ->
                cb.equal(root.get("empId"), empId);
    }
    public static Specification<Employee> nameContains(String name) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("empName")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Employee> hasDept(String dept){
        return (root, query, cb) ->
                cb.equal(root.get("deptName"), dept);
    }

    public static Specification<Employee> salaryGreaterThan(BigDecimal min) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("salary"), min);
    }

    public static Specification<Employee> salaryLessThan(BigDecimal max) {
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("salary"), max);
    }
}
