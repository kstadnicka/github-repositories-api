# GitHub Repositories API

A Spring Boot application that exposes a REST API to fetch repositories for a given GitHub user, 
along with their branches and latest commit SHA.

# Technologies Used

- Java 21
- Spring Boot 3.5
- REST API
- WireMock
- Maven
- GitHub REST API

# Usage

    mvn clean install
    mvn spring-boot:run


The application will start at:  
`http://localhost:8080`


# API Endpoint

    'GET /repositories/{userName}`

Returns a list of the user's non-forked repositories with their branches and latest commit SHA.

#  Path Variable:
    'userName` – GitHub username

# Example Request:

    GET http://localhost:8080/repositories/kstadnicka

####  Example Response:

        json
    [
     {
        "repositoryName": "books-app",
        "ownerLogin": "kstadnicka",
        "branches": [
         {
            "branchName": "main",
            "lastCommitSha": "b443a76ddca69fefac2d72df3eb9675b797df635"
        }
        ]
    }
    ]

# Error Handling

If user is not found:

     json
    {
    "status": 404,
    "message": "User kstadnicka not found."
    }


#  Configuration (application.yml)

    github.repositories-url=https://api.github.com/users/{userName}/repos
    github.branches-url=https://api.github.com/repos/{userName}/{repositoryName}/branches
    github.users-url=https://api.github.com/users/{userName}

# Testing

Run integration tests:

    mvn test

The integration tests use WireMock to simulate GitHub responses and validate real HTTP behavior.


# Author

- Kamila Stadnicka [https://www.linkedin.com/in/kamila-stadnicka-14031932a/]