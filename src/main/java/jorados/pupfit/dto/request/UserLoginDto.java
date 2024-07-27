package jorados.pupfit.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jorados.pupfit.entity.type.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserLoginDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String nickname;
    private Gender gender;
}
