package br.com.xxzidanilloxx.teammanagementapi.dto;

import java.util.List;

public record PartnerResponseDTO(
        Long id,
        String name,
        String email,
        String location,
        List<ProjectFromPartnerResponseDTO> projects) {
}
