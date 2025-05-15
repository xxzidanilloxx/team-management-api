package br.com.xxzidanilloxx.teammanagementapi.dto;

import jakarta.validation.constraints.NotNull;

public record ProjectIdentifierRequestDTO(

        @NotNull(message = "Field 'projectId' is required")
        Long projectId) {
}
