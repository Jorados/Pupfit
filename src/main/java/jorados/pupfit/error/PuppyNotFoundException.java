package jorados.pupfit.error;

import org.springframework.http.HttpStatus;

public class PuppyNotFoundException extends SeongjinException{

    public static final String MESSAGE="강아지 정보를 찾을 수 없습니다";

    public PuppyNotFoundException() {
        super(MESSAGE);
    }

    public PuppyNotFoundException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
