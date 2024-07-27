package jorados.pupfit.controller.error;

import jorados.pupfit.dto.response.ErrorResponse;
import jorados.pupfit.error.SeongjinException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


// 애플리케이션의 모든 컨트롤러에 대한 전역 예외 처리를 담당한다.
@ControllerAdvice
public class GlobalExceptionHandler {

    // API 요청 JSON 본문 값이 객체로 변환이 안될때. (@ResponseBody 에 의해 발생하는 예외)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body("HTTP REQUEST 요청이 잘못되었습니다. : " + ex.getMessage());
    }

    //@Valid 예외처리 ( 부모(상세) 예외라서 유효성 관련은 여기걸림 )
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e){
        ErrorResponse response = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .build();

        // 필드 에러항목 추가
        for (FieldError fieldError : e.getFieldErrors()) {
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return response;
    }

    // 직접만든 예외 클래스 전역 처리
    @ExceptionHandler(SeongjinException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> seongjinException(SeongjinException e){
        int statusCode = e.getStatusCode();

        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(statusCode).body(body);
    }

    // 나머지 예외처리
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러가 발생했습니다: " + ex.getMessage());
    }

}