package ks.githubrepositoriesapi;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@AllArgsConstructor
class RepositoryController {

    private final FindGithubUserRepositoriesUsecase findGithubUserRepositoriesUsecase;
    private final ValidateGithubUserExistence validateGithubUserExistence;


    @GetMapping("/repositories/{userName}")
    ResponseEntity<List<UserRepositoryResponse>> getUserRepositories(@PathVariable String userName) {
        if (!validateGithubUserExistence.checkIfGithubUserExist(userName)) {
            throw new FindGithubUserException(HttpStatusCode.valueOf(404), String.format(ErrorMessagesUtils.USER_NOT_FOUND, userName));
        }
        return new ResponseEntity<>(findGithubUserRepositoriesUsecase.findGithubUserRepositories(userName), HttpStatus.OK);
    }
}
