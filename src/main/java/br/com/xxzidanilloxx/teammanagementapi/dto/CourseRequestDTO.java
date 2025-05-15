package br.com.xxzidanilloxx.teammanagementapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CourseRequestDTO(

        @NotBlank(message = "Field 'name' is required")
        @Size(min = 3, max = 50, message = "Invalid number of characters in 'name' field")
        String name,

        @Size(max = 50, message = "Invalid number of characters in 'alias' field")
        String alias) {
}
