package ks.githubrepositoriesapi;

public class GithubRepositoriesApiApplicationTestResponses {

    public static final String GET_REPOSITORY_RESPONSE_BODY = """
                    [
                      {
                        "name": "books-app",
                        "owner": { "login": "kstadnicka" }
                      }
                    ]
                """;
    public static final String GET_BRANCH_RESPONSE_BODY = """
                    [
                      {
                        "name": "main",
                        "commit": { "sha": "b443a76ddca69fefac2d72df3eb9675b797df635" }
                      }
                    ]
                """;

    private GithubRepositoriesApiApplicationTestResponses() {
    }
}
