package jorados.pupfit.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;


// 일반 에러 메세지 전용 Reseponse
@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private String code;
    private String msg;
    private final Map<String, String> validation;

    @Builder
    public ErrorResponse(String code, String msg,Map<String,String> validation) {
        this.code = code;
        this.msg = msg;
        this.validation = validation != null ? validation : new HashMap<>();
    }

    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName,errorMessage);
    }
}
