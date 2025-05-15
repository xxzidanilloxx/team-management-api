package br.com.xxzidanilloxx.teammanagementapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PartnerRequestDTO(

        @NotBlank(message = "Field 'name' is required")
        @Size(min = 3, max = 50, message = "Invalid number of characters in 'name' field")
        String name,

        @NotBlank(message = "Field 'email' is required")
        @Email(message = "Please enter a valid email")
        String email,

        @Size(max = 100, message = "Invalid number of characters in 'location' field")
        String location) {
}
