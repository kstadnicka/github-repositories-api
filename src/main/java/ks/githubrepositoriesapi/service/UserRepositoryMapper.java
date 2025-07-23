package ks.githubrepositoriesapi.service;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
class UserRepositoryMapper {

    UserRepositoryDto mapToUserRepositoryResponse(UserRepositoryExternalDto userRepositoryExternalDto, List<RepositoryBranchExternalDto> repositoryBranches) {
        return UserRepositoryDto.builder()
                .ownerLogin(userRepositoryExternalDto.getOwner().getLogin())
                .repositoryName(userRepositoryExternalDto.getName())
                .branches(mapToUserRepositoryResponseBranches(repositoryBranches))
                .build();

    }

    private List<UserRepositoryDto.Branch> mapToUserRepositoryResponseBranches(List<RepositoryBranchExternalDto> repositoryBranches) {
        return repositoryBranches.stream()
                .map(this::mapToUserRepositoryResponseBranch)
                .toList();
    }

   private UserRepositoryDto.Branch mapToUserRepositoryResponseBranch(RepositoryBranchExternalDto repositoryBranchExternalDto) {
        return UserRepositoryDto.Branch.builder()
                .branchName(repositoryBranchExternalDto.getName())
                .lastCommitSha(repositoryBranchExternalDto.getCommit().getSha())
                .build();
    }
}
