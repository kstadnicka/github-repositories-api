package ks.githubrepositoriesapi;

import lombok.Data;

@Data
public class RepositoryBranchDto {
    String name;
    Commit commit;

    @Data
    static class Commit{
        String sha;
    }

}
