package br.com.xxzidanilloxx.teammanagementapi.exception;

import br.com.xxzidanilloxx.teammanagementapi.infra.ErrorType;
import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException{
    private final ErrorType errorType = ErrorType.RESOURCE_NOT_FOUND;

    public ResourceNotFoundException(Class<?> x , Long id) {
        super(String.format("%s not found with id '%d'.", x.getSimpleName(), id));
    }

    public ResourceNotFoundException(String field, String value) {
        super(String.format("Student not found with %s '%s'.", field, value));
    }

    public ResourceNotFoundException(Long studentId, Long teamId){
        super(String.format("No membership found for student (id: '%d') in team (id: '%d').", studentId, teamId));
    }

    public ResourceNotFoundException(Long courseId, Long studentId, Long teamId){
        super(String.format("No membership found for course (id: '%d'), student (id: '%d') and/or team (id: '%d').",
                courseId, studentId, teamId));
    }
}
