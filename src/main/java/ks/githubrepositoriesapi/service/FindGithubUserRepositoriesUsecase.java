package ks.githubrepositoriesapi.service;

import ks.githubrepositoriesapi.utils.FindGithubUserException;

import java.util.List;

public interface FindGithubUserRepositoriesUsecase {
    List<UserRepositoryDto> findGithubUserRepositories(String userName) throws FindGithubUserException;
}
