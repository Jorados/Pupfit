package jorados.pupfit.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResponseDto<T> {
    private final Integer code; // 1 성공, -1 실패, 99 관리자 성공, -99 관리자 실패
    private final String msg;
    private final T data;
}
