package br.com.xxzidanilloxx.teammanagementapi.mapper;

import br.com.xxzidanilloxx.teammanagementapi.dto.StudentResponseDTO;
import br.com.xxzidanilloxx.teammanagementapi.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "name", expression = "java(student.getFirstName() + \" \" + student.getLastName())")
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "birthDate", source = "birthDate")
    @Mapping(target = "gender", source = "gender")
    @Mapping(target = "courseName", source = "course.name")
    @Mapping(target = "ra", source = "ra")
    @Mapping(target = "status", source = "status")
    StudentResponseDTO toDto(Student student);
}
