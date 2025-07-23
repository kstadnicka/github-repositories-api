package ks.githubrepositoriesapi;

import lombok.Data;

@Data
public class UserRepositoryDto {

    String name;
    Owner owner;
    Boolean fork;

    @Data
    static class Owner{
        String login;
    }
}
