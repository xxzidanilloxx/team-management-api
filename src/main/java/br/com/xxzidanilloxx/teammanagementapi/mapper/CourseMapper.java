package br.com.xxzidanilloxx.teammanagementapi.mapper;

import br.com.xxzidanilloxx.teammanagementapi.dto.CourseResponseDTO;
import br.com.xxzidanilloxx.teammanagementapi.dto.StudentFromCourseResponseDTO;
import br.com.xxzidanilloxx.teammanagementapi.entity.Course;
import br.com.xxzidanilloxx.teammanagementapi.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(target = "students", source = "students", qualifiedByName = "mapStudents")
    CourseResponseDTO toDto(Course course);

    @Named("mapStudents")
    static List<StudentFromCourseResponseDTO> mapStudents(List<Student> students){
        return students.stream()
                .map(student -> new StudentFromCourseResponseDTO(
                        student.getFirstName() + " " + student.getLastName(),
                        student.getRa()
                ))
                .collect(Collectors.toList());
    }
}
