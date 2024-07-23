package jorados.pupfit.dto;

import jakarta.validation.constraints.NotBlank;
import jorados.pupfit.entity.type.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserDto {

    @NotBlank
    private String username; // 시큐리티 로그인 ID

    @NotBlank
    private String password; // 암호

    @NotBlank
    private String nickname; // 닉네임

    @NotBlank
    private Gender gender; // 성별 ("M","W")
}
