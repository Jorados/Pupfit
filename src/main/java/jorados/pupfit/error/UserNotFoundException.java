package jorados.pupfit.error;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends SeongjinException{

    public static final String MESSAGE = "회원을 찾을 수 없습니다.";

    public UserNotFoundException() {
        super(MESSAGE);
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}

