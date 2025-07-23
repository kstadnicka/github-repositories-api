package ks.githubrepositoriesapi;

public class ErrorMessagesUtils {

    public static final String USER_NOT_FOUND = "Github user %s cannot be found";
    public static final String REPOSITORY_NOT_FOUND = "GitHub user repositories cound not be retrieved for provided user %s";
    public static final String BRANCHES_NOT_FOUND = "GitHub user repository branches cound not be retrieved for provided user %s and repository %s";

    private ErrorMessagesUtils() {
    }
}
