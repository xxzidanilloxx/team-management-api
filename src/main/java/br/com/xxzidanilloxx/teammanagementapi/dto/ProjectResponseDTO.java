package br.com.xxzidanilloxx.teammanagementapi.dto;

public record ProjectResponseDTO(
        Long id,
        String partnerName,
        String name,
        String description
) {
}
