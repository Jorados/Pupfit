package jorados.pupfit.dto;


import jakarta.validation.constraints.NotBlank;
import jorados.pupfit.entity.Puppy;
import jorados.pupfit.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserPuppyDto {

    private Long id;

    @NotBlank
    private String puppyName;

    @NotBlank
    private Long userId;

    @NotBlank
    private Long puppyId;
}
