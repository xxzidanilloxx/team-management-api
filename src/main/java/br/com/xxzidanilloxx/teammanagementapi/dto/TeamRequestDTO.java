package br.com.xxzidanilloxx.teammanagementapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TeamRequestDTO(

        @NotBlank(message = "Field 'name' is required")
        @Size(min = 3, max = 50, message = "Invalid number of characters in 'name' field")
        String name) {
}
