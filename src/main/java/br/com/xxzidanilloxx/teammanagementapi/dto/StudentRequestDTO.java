package br.com.xxzidanilloxx.teammanagementapi.dto;

import br.com.xxzidanilloxx.teammanagementapi.enumeration.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record StudentRequestDTO(

        @NotBlank(message = "Field 'firstName' is required")
        @Size(min = 2, max = 15, message = "Invalid number of characters in 'firstName' field")
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ ]+$", message = "The 'firstName' field must contain only letters")
        String firstName,

        @NotBlank(message = "Field 'lastName' is required")
        @Size(min = 2, max = 15, message = "Invalid number of characters in 'lastName' field")
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ ]+$", message = "The 'lastName' field must contain only letters")
        String lastName,

        @NotBlank(message = "Field 'cpf' is required")
        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{11}", message = "Please enter a valid CPF")
        String cpf,

        @NotBlank(message = "Field 'email' is required")
        @Email(message = "Please enter a valid email")
        String email,

        @NotNull(message = "Field 'birthdate' is required")
        @Past(message = "The birthdate must be in the past")
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate birthDate,

        @NotNull(message = "Field 'gender' is required")
        Gender gender,

        @NotNull(message = "Field 'course' is required")
        Long courseId,

        @NotBlank(message = "Field 'ra' is required")
        @Pattern(regexp = "\\d{8}", message = "The 'ra' field must contain exactly 8 digits")
        String ra,

        @NotNull(message = "Field 'status' is required")
        Boolean status) {
}
