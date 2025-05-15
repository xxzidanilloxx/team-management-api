package br.com.xxzidanilloxx.teammanagementapi.exception;

import br.com.xxzidanilloxx.teammanagementapi.infra.ErrorType;
import lombok.Getter;

@Getter
public class TeamCourseMismatchException extends RuntimeException {
    private final ErrorType errorType = ErrorType.TEAM_COURSE_MISMATCH;

    public TeamCourseMismatchException() {
        super("All members in a team must belong to the same course");
    }
}
