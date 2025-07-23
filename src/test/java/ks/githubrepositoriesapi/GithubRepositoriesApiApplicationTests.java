package ks.githubrepositoriesapi;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import ks.githubrepositoriesapi.api.UserRepositoryResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "github.users-url=http://localhost:1234/users/{userName}",
        "github.repositories-url=http://localhost:1234/users/{userName}/repos",
        "github.branches-url=http://localhost:1234/repos/{userName}/{repositoryName}/branches"
})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GithubRepositoriesApiApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private WireMockServer wireMockServer;

    @BeforeAll
    void startWireMock() {
        wireMockServer = new WireMockServer(1234);
        wireMockServer.start();
        WireMock.configureFor("localhost", 1234);
    }

    @AfterAll
    void stopWireMock() {
        wireMockServer.stop();
    }

    @Test
    void shouldReturnValidRepositoriesForExistingUser() {
        //given
        String userName = "kstadnicka";
        String getUserResponseBody = GithubRepositoriesApiApplicationTestResponses.GET_REPOSITORY_RESPONSE_BODY;
        String getBranchResponseBody =GithubRepositoriesApiApplicationTestResponses.GET_BRANCH_RESPONSE_BODY;
        String url = "http://localhost:" + port + "/repositories/" + userName;

        //when
        stubFor(get(urlEqualTo("/users/" + userName))
                .willReturn(ok()));
        stubFor(get(urlEqualTo("/users/" + userName + "/repos"))
                .willReturn(okJson(getUserResponseBody)));
        stubFor(get(urlEqualTo("/repos/" + userName + "/books-app/branches"))
                .willReturn(okJson(getBranchResponseBody)));

        //then
        ResponseEntity<UserRepositoryResponse[]> response =
                restTemplate.getForEntity(url, UserRepositoryResponse[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(1);

        UserRepositoryResponse repo = response.getBody()[0];
        assertThat(repo.getRepositoryName()).isEqualTo("books-app");
        assertThat(repo.getOwnerLogin()).isEqualTo("kstadnicka");
        assertThat(repo.getBranches()).hasSize(1);
        assertThat(repo.getBranches().getFirst().getBranchName()).isEqualTo("main");
        assertThat(repo.getBranches().getFirst().getLastCommitSha()).isEqualTo("b443a76ddca69fefac2d72df3eb9675b797df635");
    }
}