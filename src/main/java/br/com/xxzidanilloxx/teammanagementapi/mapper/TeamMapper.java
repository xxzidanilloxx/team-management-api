package br.com.xxzidanilloxx.teammanagementapi.mapper;

import br.com.xxzidanilloxx.teammanagementapi.dto.MemberFromTeamResponseDTO;
import br.com.xxzidanilloxx.teammanagementapi.dto.TeamResponseDTO;
import br.com.xxzidanilloxx.teammanagementapi.entity.Membership;
import br.com.xxzidanilloxx.teammanagementapi.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    @Mapping(target = "memberships", source = "members", qualifiedByName = "mapMemberships")
    @Mapping(target = "projectName", source = "project.name")
    TeamResponseDTO toDto(Team team);

    @Named("mapMemberships")
    static List<MemberFromTeamResponseDTO> mapMemberships(List<Membership> memberships) {
        return memberships.stream()
                .map(member -> new MemberFromTeamResponseDTO(
                        member.getStudent().getId(),
                        member.getStudent().getFirstName() + " " + member.getStudent().getLastName(),
                        member.getRole()))
                .collect(Collectors.toList());
    }
}
