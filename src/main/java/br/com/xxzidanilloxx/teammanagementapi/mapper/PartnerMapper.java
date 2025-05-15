package br.com.xxzidanilloxx.teammanagementapi.mapper;

import br.com.xxzidanilloxx.teammanagementapi.dto.PartnerResponseDTO;
import br.com.xxzidanilloxx.teammanagementapi.dto.ProjectFromPartnerResponseDTO;
import br.com.xxzidanilloxx.teammanagementapi.entity.Partner;
import br.com.xxzidanilloxx.teammanagementapi.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PartnerMapper {

    @Mapping(target = "projects", source = "projects", qualifiedByName = "mapProjects")
    PartnerResponseDTO toDto(Partner partner);

    @Named("mapProjects")
    static List<ProjectFromPartnerResponseDTO> mapProjects(List<Project> projects) {
        return projects.stream()
                .map(project -> new ProjectFromPartnerResponseDTO(project.getName()))
                .collect(Collectors.toList());
    }
}
