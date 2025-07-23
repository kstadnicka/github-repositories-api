package ks.githubrepositoriesapi;

import org.springframework.http.HttpStatusCode;

public class FindGithubUserException extends RuntimeException {

    HttpStatusCode statusCode;

    public FindGithubUserException(HttpStatusCode statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
