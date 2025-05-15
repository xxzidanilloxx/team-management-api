package br.com.xxzidanilloxx.teammanagementapi.dto;

import br.com.xxzidanilloxx.teammanagementapi.enumeration.Role;

public record MemberFromTeamResponseDTO(Long id,
                                        String name,
                                        Role role) {
}
