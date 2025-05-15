package br.com.xxzidanilloxx.teammanagementapi.mapper;

import br.com.xxzidanilloxx.teammanagementapi.dto.MembershipResponseDTO;
import br.com.xxzidanilloxx.teammanagementapi.entity.Course;
import br.com.xxzidanilloxx.teammanagementapi.entity.Membership;
import br.com.xxzidanilloxx.teammanagementapi.entity.Student;
import br.com.xxzidanilloxx.teammanagementapi.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MembershipMapper {

    @Mapping(target = "membershipId", source = "id")
    @Mapping(target = "courseName", source = "course", qualifiedByName = "mapCourseName")
    @Mapping(target = "studentName", source = "student", qualifiedByName = "mapStudentName")
    @Mapping(target = "teamName", source = "team", qualifiedByName = "mapTeamName")
    @Mapping(target = "role", source = "role")
    MembershipResponseDTO toDto(Membership membership);

    @Named("mapCourseName")
    static String mapCourseName(Course course) {
        return course.getName();
    }

    @Named("mapStudentName")
    static String mapStudentName(Student student) {
        return student.getFirstName() + " " + student.getLastName();
    }

    @Named("mapTeamName")
    static String mapTeamName(Team team) {
        return team.getName();
    }
}
