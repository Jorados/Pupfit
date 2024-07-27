package jorados.pupfit.controller.error;

import jorados.pupfit.dto.response.ErrorResponse;
import jorados.pupfit.error.SeongjinException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


// 애플리케이션의 모든 컨트롤러에 대한 전역 예외 처리를 담당한다.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // API 요청 JSON 본문 값이 객체로 변환이 안될때. (@ResponseBody 에 의해 발생하는 예외) --> 확인해봐야함.
    @ExceptionHandler(HttpMessageConversionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageConversionException e) {
        ErrorResponse response = ErrorResponse.builder()
                .code("400")
                .msg("잘못된 JSON 요청 형식입니다." + e.getMessage())
                .build();

        return response;
    }

    //@Valid 예외처리 ( 부모(상세) 예외라서 유효성 관련은 여기걸림 )
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e){
        ErrorResponse response = ErrorResponse.builder()
                .code("400")
                .msg("잘못된 필드 유효성 요청 형식입니다.")
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
                .msg(e.getMessage())
                .build();

        return ResponseEntity.status(statusCode).body(body);
    }

    // 나머지 예외처리
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneralException(Exception e) {
        ErrorResponse response = ErrorResponse.builder()
                .code("400")
                .msg("서버 에러가 발생했습니다 : " + e.getMessage())
                .build();
        return response;
    }

}