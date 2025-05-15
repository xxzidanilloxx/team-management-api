package br.com.xxzidanilloxx.teammanagementapi.exception;

import br.com.xxzidanilloxx.teammanagementapi.infra.ErrorType;
import lombok.Getter;

@Getter
public class InvalidPaginationParametersException extends RuntimeException{
    private final ErrorType errorType = ErrorType.INVALID_PAGINATION_PARAMETERS;

    public InvalidPaginationParametersException(int page, int size) {
        super(String.format(
                "Invalid pagination parameters: page = '%d', size = '%d'. " +
                "Page must be >= 0 and size must be between 1 and 100.", page, size
        ));
    }
}
