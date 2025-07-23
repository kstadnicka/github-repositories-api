package ks.githubrepositoriesapi;


import lombok.AllArgsConstructor;
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
public class UserRepositoryService implements FindGithubUserRepositoriesUsecase {

    @Value("${github.repositories-url}")
    private String getRepositoriesUrl;

    @Value("${github.branches-url}")
    private String getBranchesUrl;

    private final RestTemplate restTemplate;

    @Override
    public List<UserRepositoryResponse> findGithubUserRepositories(String userName) throws FindGithubUserException {
        ResponseEntity<List<UserRepositoryDto>> response = restTemplate.exchange(
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

        List<UserRepositoryDto> userRepositories = Optional.ofNullable(response.getBody()).orElse(new ArrayList<>());
       return userRepositories.stream()
                .map(userRepositoryDto -> findRepositoryBranches(userName,userRepositoryDto))
                .toList();
    }

    private UserRepositoryResponse findRepositoryBranches(String userName, UserRepositoryDto userRepositoryDto) throws FindGithubUserException {
        ResponseEntity<List<RepositoryBranchDto>> response = restTemplate.exchange(
                getBranchesUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                Map.of("userName",userName,"repositoryName",userRepositoryDto.getName())
        );
        if(!response.getStatusCode().is2xxSuccessful()){
            throw new FindGithubUserException(response.getStatusCode(),String.format(ErrorMessagesUtils.BRANCHES_NOT_FOUND,userName, userRepositoryDto.getName()));
        }
        return mapToUserRepositoryResponse(userRepositoryDto,response.getBody());
    }

    private UserRepositoryResponse mapToUserRepositoryResponse(UserRepositoryDto userRepositoryDto, List<RepositoryBranchDto> repositoryBranches) {
        return UserRepositoryResponse.builder()
                .ownerLogin(userRepositoryDto.getOwner().getLogin())
                .repositoryName(userRepositoryDto.getName())
                .branches(mapToUserRepositoryResponseBranches(repositoryBranches))
                .build();

    }

    private List<UserRepositoryResponse.Branch> mapToUserRepositoryResponseBranches(List<RepositoryBranchDto> repositoryBranches) {
        return repositoryBranches.stream()
                .map(this::mapToUserRepositoryResponseBranch)
                .toList();
    }

    private UserRepositoryResponse.Branch mapToUserRepositoryResponseBranch(RepositoryBranchDto repositoryBranchDto) {
        return UserRepositoryResponse.Branch.builder()
                .branchName(repositoryBranchDto.getName())
                .lastCommitSha(repositoryBranchDto.getCommit().getSha())
                .build();
    }
}
