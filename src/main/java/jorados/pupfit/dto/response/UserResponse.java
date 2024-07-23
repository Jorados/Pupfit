package jorados.pupfit.dto.response;


import jakarta.validation.constraints.NotBlank;
import jorados.pupfit.entity.type.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserResponse {

    @NotBlank
    private String nickname; // 닉네임

    @NotBlank
    private Gender gender; // 성별 ("M","W")
}
