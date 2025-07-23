package ks.githubrepositoriesapi.service;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserRepositoryDto {

    private String repositoryName;
    private String ownerLogin;
    private List<Branch> branches;

        @Data
        @Builder
        public static class Branch {
            private String branchName;
            private String lastCommitSha;
        }
}
