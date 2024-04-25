package comspringboot.challengewl.exceptions.handler;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ExceptionFilters {

    private Integer status;
    private String message;
    private LocalDateTime timestamp;

}
