package ks.githubrepositoriesapi.api;

import ks.githubrepositoriesapi.service.UserRepositoryDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class ApiResponseMapper {

    List<UserRepositoryResponse> mapDtosToResponses(List<UserRepositoryDto> githubUserRepositories) {
        return githubUserRepositories.stream()
                .map(this::mapDtosToResponse)
                .toList();
    }

    private UserRepositoryResponse mapDtosToResponse(UserRepositoryDto userRepositoryDto) {
        return UserRepositoryResponse.builder()
                .ownerLogin(userRepositoryDto.getOwnerLogin())
                .repositoryName(userRepositoryDto.getRepositoryName())
                .branches(mapToResponseBranches(userRepositoryDto.getBranches()))
                .build();
    }

    private List<UserRepositoryResponse.Branch> mapToResponseBranches(List<UserRepositoryDto.Branch> branches) {
        return branches.stream()
                .map(this::mapToResponseBranch)
                .toList();
    }

    private UserRepositoryResponse.Branch mapToResponseBranch(UserRepositoryDto.Branch branch) {
        return UserRepositoryResponse.Branch.builder()
                .branchName(branch.getBranchName())
                .lastCommitSha(branch.getLastCommitSha())
                .build();
    }
}
