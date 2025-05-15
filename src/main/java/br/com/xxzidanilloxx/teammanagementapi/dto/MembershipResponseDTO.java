package br.com.xxzidanilloxx.teammanagementapi.dto;

import br.com.xxzidanilloxx.teammanagementapi.enumeration.Role;

public record MembershipResponseDTO(
        Long membershipId,
        String courseName,
        String studentName,
        String teamName,
        Role role
) {
}
