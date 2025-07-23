package ks.githubrepositoriesapi;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class ErrorResponse {
    String status;
    String message;
}
