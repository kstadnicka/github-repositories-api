package ks.githubrepositoriesapi;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
class UserValidatorService implements ValidateGithubUserExistence {

    @Value("${github.users-url}")
    private String getUsersUrl;

    private final RestTemplate restTemplate;

    @Override
    public boolean checkIfGithubUserExist(String userName) {
        try {
            ResponseEntity<Void> response = restTemplate.exchange(
                    getUsersUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    },
                    userName
            );
            return response.getStatusCode().value() == 200;
        } catch (HttpClientErrorException e) {
            throw new FindGithubUserException(HttpStatusCode.valueOf(404), String.format(ErrorMessagesUtils.USER_NOT_FOUND, userName));
        }
    }
}
