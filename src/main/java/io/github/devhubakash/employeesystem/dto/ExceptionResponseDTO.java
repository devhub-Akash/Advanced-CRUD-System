package io.github.devhubakash.employeesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponseDTO {

    private LocalDateTime timestamp;
    private int statusCode;
    private String error;
    private String message;
    private String path;

}
