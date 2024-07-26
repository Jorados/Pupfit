package jorados.pupfit.error;

import java.util.HashMap;
import java.util.Map;

public abstract class SeongjinException extends RuntimeException{

    private final Map<String,String> validation = new HashMap<>();

    public SeongjinException() {
        super();
    }

    public SeongjinException(String message) {
        super(message);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message){
        validation.put(fieldName,message);
    }


}
