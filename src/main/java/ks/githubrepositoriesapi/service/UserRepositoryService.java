package ks.githubrepositoriesapi.service;


import ks.githubrepositoriesapi.utils.ErrorMessagesUtils;
import ks.githubrepositoriesapi.utils.FindGithubUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
class UserRepositoryService implements FindGithubUserRepositoriesUsecase {

    @Value("${github.repositories-url}")
    private String getRepositoriesUrl;

    @Value("${github.branches-url}")
    private String getBranchesUrl;

    private final RestTemplate restTemplate;
    private final UserRepositoryMapper userRepositoryMapper;

    @Override
    public List<UserRepositoryDto> findGithubUserRepositories(String userName) throws FindGithubUserException {
        ResponseEntity<List<UserRepositoryExternalDto>> response = restTemplate.exchange(
                getRepositoriesUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                userName
        );
        if(!response.getStatusCode().is2xxSuccessful()){
            throw new FindGithubUserException(response.getStatusCode(),String.format(ErrorMessagesUtils.REPOSITORY_NOT_FOUND,userName));
        }

        List<UserRepositoryExternalDto> userRepositories = Optional.ofNullable(response.getBody()).orElse(new ArrayList<>());
       return userRepositories.stream()
                .map(userRepositoryExternalDto -> findRepositoryBranches(userName, userRepositoryExternalDto))
                .toList();
    }

    private UserRepositoryDto findRepositoryBranches(String userName, UserRepositoryExternalDto userRepositoryExternalDto) throws FindGithubUserException {
        ResponseEntity<List<RepositoryBranchExternalDto>> response = restTemplate.exchange(
                getBranchesUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                Map.of("userName",userName,"repositoryName", userRepositoryExternalDto.getName())
        );
        if(!response.getStatusCode().is2xxSuccessful()){
            throw new FindGithubUserException(response.getStatusCode(),String.format(ErrorMessagesUtils.BRANCHES_NOT_FOUND,userName, userRepositoryExternalDto.getName()));
        }
        return userRepositoryMapper.mapToUserRepositoryResponse(userRepositoryExternalDto,response.getBody());
    }
}
