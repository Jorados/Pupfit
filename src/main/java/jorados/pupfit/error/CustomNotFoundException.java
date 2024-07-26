package jorados.pupfit.error;

import org.springframework.http.HttpStatus;

public class CustomNotFoundException extends SeongjinException{
    public static final String MESSAGE = "를 찾을 수 없습니다";


    public CustomNotFoundException() {
        super();
    }

    public CustomNotFoundException(String message) {
        super(message + MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
