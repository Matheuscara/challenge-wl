package comspringboot.challengewl.exceptions.errors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserErrorResponse {

    private Integer status;
    private String message;
    private Long timeStamp;

}
