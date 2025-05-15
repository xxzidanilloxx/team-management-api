package br.com.xxzidanilloxx.teammanagementapi.dto;

import br.com.xxzidanilloxx.teammanagementapi.enumeration.Role;
import jakarta.validation.constraints.NotNull;

public record MembershipRequestDTO(

        @NotNull(message = "Field 'courseId' is required")
        Long courseId,

        @NotNull(message = "Field 'studentId' is required")
        Long studentId,

        @NotNull(message = "Field 'teamId' is required")
        Long teamId,

        @NotNull(message = "Field 'role' is required")
        Role role) {
}
