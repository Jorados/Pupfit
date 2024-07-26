package jorados.pupfit.error;

public class CustomNotFoundException extends SeongjinException{
    public static final String MESSAGE = "를 찾을 수 없습니다";
    @Override
    public int getStatusCode() {
        return 0;
    }

    public CustomNotFoundException() {
        super();
    }

    public CustomNotFoundException(String message) {
        super(message + MESSAGE);
    }
}
