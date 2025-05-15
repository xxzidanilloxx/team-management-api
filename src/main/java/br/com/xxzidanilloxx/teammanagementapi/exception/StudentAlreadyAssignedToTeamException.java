package br.com.xxzidanilloxx.teammanagementapi.exception;

import br.com.xxzidanilloxx.teammanagementapi.infra.ErrorType;
import lombok.Getter;

@Getter
public class StudentAlreadyAssignedToTeamException extends RuntimeException {
    private final ErrorType errorType = ErrorType.USER_ALREADY_ASSIGNED_TO_TEAM;

    public StudentAlreadyAssignedToTeamException(Long studentId) {
        super(String.format("Student with id '%d' is already assigned to a team.", studentId));
    }
}
