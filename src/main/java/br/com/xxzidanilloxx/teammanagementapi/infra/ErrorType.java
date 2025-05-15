package br.com.xxzidanilloxx.teammanagementapi.infra;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    RESOURCE_NOT_FOUND(404, HttpStatus.NOT_FOUND, "Resource not found"),
    RESOURCE_ALREADY_EXISTS(409, HttpStatus.CONFLICT, "Resource already exists"),
    USER_ALREADY_ASSIGNED_TO_TEAM(400, HttpStatus.BAD_REQUEST, "Student is already assigned to the team"),
    TEAM_COURSE_MISMATCH(400, HttpStatus.BAD_REQUEST, "All members in a team must belong to the same course"),
    DUPLICATE_ROLE(400, HttpStatus.BAD_REQUEST, "A team can only have one Scrum Master and one Product Owner"),
    DELETION_NOT_ALLOWED(403, HttpStatus.FORBIDDEN, "Deletion is not allowed"),
    INVALID_PAGINATION_PARAMETERS(400, HttpStatus.BAD_REQUEST, "Invalid pagination parameters"),
    VALIDATION_ERROR(400, HttpStatus.BAD_REQUEST, "Validation error"),
    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected internal server error occurred");

    private final Integer errorCode;
    private final HttpStatus httpStatus;
    private final String error;
}
