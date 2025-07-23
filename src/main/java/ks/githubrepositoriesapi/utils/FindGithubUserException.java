package ks.githubrepositoriesapi.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatusCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FindGithubUserException extends RuntimeException {

    HttpStatusCode statusCode;

    public FindGithubUserException(HttpStatusCode statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
