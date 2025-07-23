package ks.githubrepositoriesapi;

import java.util.List;

public interface FindGithubUserRepositoriesUsecase {
    List<UserRepositoryResponse> findGithubUserRepositories(String userName) throws FindGithubUserException;
}
