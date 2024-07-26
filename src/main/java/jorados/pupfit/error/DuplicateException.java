package jorados.pupfit.error;

import org.springframework.http.HttpStatus;

public class DuplicateException extends SeongjinException{

    private static final String MESSAGE = "이미 존재하는 회원아이디입니다.";

    public DuplicateException() {
        super(MESSAGE);
    }

    public DuplicateException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.CONFLICT.value();
    }


}
