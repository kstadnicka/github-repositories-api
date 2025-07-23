package ks.githubrepositoriesapi;

import lombok.Data;
import org.springframework.http.HttpStatusCode;

@Data
public class FindGithubUserException extends RuntimeException {

    HttpStatusCode statusCode;

    public FindGithubUserException(HttpStatusCode statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
