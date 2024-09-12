package caroneiros.infra.exception_handler;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiError {

    private LocalDateTime timetamp;
    private int status;
    private String erro;
    private List<String> errors;
    private String path;
}
