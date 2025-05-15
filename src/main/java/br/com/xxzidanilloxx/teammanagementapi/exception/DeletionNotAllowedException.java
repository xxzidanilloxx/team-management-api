package br.com.xxzidanilloxx.teammanagementapi.exception;

import br.com.xxzidanilloxx.teammanagementapi.infra.ErrorType;
import lombok.Getter;

@Getter
public class DeletionNotAllowedException extends RuntimeException{
    private final ErrorType errorType = ErrorType.DELETION_NOT_ALLOWED;

    public DeletionNotAllowedException() {
        super("Deletion is not allowed while students are registered.");
    }
}
