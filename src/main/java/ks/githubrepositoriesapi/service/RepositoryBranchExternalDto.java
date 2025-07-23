package ks.githubrepositoriesapi.service;

import lombok.Data;

@Data
class RepositoryBranchExternalDto {
    private String name;
    private Commit commit;

    @Data
    static class Commit{
       private String sha;
    }
}
