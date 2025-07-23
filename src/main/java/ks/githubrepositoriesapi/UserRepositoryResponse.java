package ks.githubrepositoriesapi;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserRepositoryResponse {

    String repositoryName;
    String ownerLogin;
    List<Branch> branches;

        @Data
        @Builder
        static class Branch{
            String branchName;
            String lastCommitSha;
        }
}
