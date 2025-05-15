package br.com.xxzidanilloxx.teammanagementapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProjectRequestDTO(

        @NotNull(message = "Field 'partnerId' is required")
        Long partnerId,

        @NotBlank(message = "Field 'name' is required")
        @Size(min = 3, max = 50, message = "Invalid number of characters in 'name' field")
        String name,

        @Size(max = 255, message = "Invalid number of characters in 'description' field")
        String description
) {
}
