package br.com.xxzidanilloxx.teammanagementapi.exception;

import br.com.xxzidanilloxx.teammanagementapi.infra.ErrorType;
import lombok.Getter;

@Getter
public class ResourceAlreadyExistsException extends RuntimeException{
    private final ErrorType errorType = ErrorType.RESOURCE_ALREADY_EXISTS;

    public ResourceAlreadyExistsException(Class<?> x , String name) {
        super(String.format("%s already exists with name '%s'.", x.getSimpleName(), name));
    }

    public ResourceAlreadyExistsException(String field, String value) {
        super(String.format("%s '%s' already exists with name.", field, value));
    }

    public ResourceAlreadyExistsException(String field1, String value1, String field2, String value2) {
        super(String.format("%s '%s' already exists with name.", field1, value1, field2, value2));
    }
}
