package br.com.xxzidanilloxx.teammanagementapi.infra;

import br.com.xxzidanilloxx.teammanagementapi.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private ResponseEntity<RestErrorMessage> buildErrorResponse(ErrorType errorType, String message, WebRequest request) {
        RestErrorMessage errorMessage = new RestErrorMessage(
                errorType.getErrorCode(),
                errorType.getHttpStatus(),
                errorType.getError(),
                message,
                request.getDescription(false)
        );
        return ResponseEntity.status(errorType.getHttpStatus()).body(errorMessage);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<RestErrorMessage> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {
        return buildErrorResponse(ErrorType.RESOURCE_NOT_FOUND, e.getMessage(), request);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<RestErrorMessage> handleResourceAlreadyExistsException(ResourceAlreadyExistsException e, WebRequest request) {
        return buildErrorResponse(e.getErrorType(), e.getMessage(), request);
    }

    @ExceptionHandler(StudentAlreadyAssignedToTeamException.class)
    public ResponseEntity<RestErrorMessage> handleStudentAlreadyAssignedToTeamException(StudentAlreadyAssignedToTeamException e, WebRequest request) {
        return buildErrorResponse(e.getErrorType(), e.getMessage(), request);
    }

    @ExceptionHandler(DuplicateRoleException.class)
    public ResponseEntity<RestErrorMessage> handleDuplicateRoleException(DuplicateRoleException e, WebRequest request) {
        return buildErrorResponse(e.getErrorType(), e.getMessage(), request);
    }

    @ExceptionHandler(TeamCourseMismatchException.class)
    public ResponseEntity<RestErrorMessage> handleTeamCourseMismatchException(TeamCourseMismatchException e, WebRequest request) {
        return buildErrorResponse(e.getErrorType(), e.getMessage(), request);
    }

    @ExceptionHandler(DeletionNotAllowedException.class)
    public ResponseEntity<RestErrorMessage> handleDeletionNotAllowedException(DeletionNotAllowedException e, WebRequest request) {
        return buildErrorResponse(e.getErrorType(), e.getMessage(), request);
    }

    @ExceptionHandler(InvalidPaginationParametersException.class)
    public ResponseEntity<RestErrorMessage> handleInvalidPaginationParamsException(InvalidPaginationParametersException e, WebRequest request) {
        return buildErrorResponse(e.getErrorType(), e.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestErrorMessage> handleValidationException(MethodArgumentNotValidException e, WebRequest request) {
        List<String> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        ErrorType errorType = ErrorType.VALIDATION_ERROR;

        RestErrorMessage errorMessage = new RestErrorMessage(
                errorType.getErrorCode(),
                errorType.getHttpStatus(),
                errorType.getError(),
                String.join("; ", errors),
                request.getDescription(false)
        );

        return ResponseEntity.status(errorType.getHttpStatus()).body(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestErrorMessage> handleGenericException(WebRequest request) {
        ErrorType errorType = ErrorType.INTERNAL_SERVER_ERROR;
        return buildErrorResponse(errorType, "An unexpected error occurred.", request);
    }
}
