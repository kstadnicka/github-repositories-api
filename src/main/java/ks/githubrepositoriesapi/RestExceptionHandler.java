package ks.githubrepositoriesapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(FindGithubUserException.class)
    ResponseEntity<ErrorResponse> handleFindGithubUserException(FindGithubUserException exception, WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.valueOf(exception.getStatusCode().value()))
                .body(ErrorResponse.builder()
                        .status(String.valueOf(exception.getStatusCode().value()))
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ErrorResponse> handleRunTimeException(RuntimeException exception, WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                        .build());
    }
}
