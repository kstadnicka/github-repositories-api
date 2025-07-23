package ks.githubrepositoriesapi.service;

import lombok.Data;

@Data
class UserRepositoryExternalDto {

    private String name;
    private Owner owner;
    private Boolean fork;

    @Data
    static class Owner{
        private String login;
    }
}
