package br.com.xxzidanilloxx.teammanagementapi.dto;

import java.util.List;

public record TeamResponseDTO(
        Long id,
        String name,
        List<MemberFromTeamResponseDTO> memberships,
        String projectName) {
}
