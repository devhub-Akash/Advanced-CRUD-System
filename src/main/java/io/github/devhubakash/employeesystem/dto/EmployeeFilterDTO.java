package io.github.devhubakash.employeesystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;


@Data
@Schema(description = "Filter criteria for employee search")
public class EmployeeFilterDTO {

    @Schema(description = "Exact employee Id", example = "EMP001")
    private String empId;

    @Schema(description = "Partial name match (Case in-sensitive)", example = "john")
    private String empName;

    @Schema(description = "Exact department name", example = "IT")
    private String deptName;

    @Schema(description = "Minimum salary (inclusive)", example = "50000")
    private BigDecimal minSalary;

    @Schema(description = "Maximum salary (inclusive)", example = "1000000")
    private BigDecimal maxSalary;
}
