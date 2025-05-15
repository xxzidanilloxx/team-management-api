package br.com.xxzidanilloxx.teammanagementapi.mapper;

import br.com.xxzidanilloxx.teammanagementapi.dto.ProjectResponseDTO;
import br.com.xxzidanilloxx.teammanagementapi.entity.Partner;
import br.com.xxzidanilloxx.teammanagementapi.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(source = "partner", target = "partnerName", qualifiedByName = "mapPartnerName")
    ProjectResponseDTO toDto(Project project);

    @Named("mapPartnerName")
    static String mapPartnerName(Partner partner) {
        return partner.getName();
    }
}

