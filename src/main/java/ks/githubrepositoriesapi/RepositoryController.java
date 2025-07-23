package ks.githubrepositoriesapi;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@AllArgsConstructor
class RepositoryController {

    private final FindGithubUserRepositoriesUsecase findGithubUserRepositoriesUsecase;


    @GetMapping("/repositories/{userName}")
    List<UserRepositoryResponse> getUserRepositories(@PathVariable String userName){
    return findGithubUserRepositoriesUsecase.findGithubUserRepositories(userName);
    }
}
