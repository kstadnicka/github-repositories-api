package ks.githubrepositoriesapi.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class ErrorResponse {
    String status;
    String message;
}
