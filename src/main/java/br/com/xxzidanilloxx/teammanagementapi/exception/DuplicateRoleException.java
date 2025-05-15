package br.com.xxzidanilloxx.teammanagementapi.exception;

import br.com.xxzidanilloxx.teammanagementapi.infra.ErrorType;
import lombok.Getter;

@Getter
public class DuplicateRoleException extends RuntimeException {
    private final ErrorType errorType = ErrorType.DUPLICATE_ROLE;

    public DuplicateRoleException() {
        super("A team can only have 1 (one) Scrum Master and 1 (one) Product Owner");
    }
}
