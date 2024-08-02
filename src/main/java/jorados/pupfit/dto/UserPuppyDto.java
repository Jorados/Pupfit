package jorados.pupfit.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jorados.pupfit.entity.Puppy;
import jorados.pupfit.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPuppyDto {

    private Long id;

    private Long userId;

    private String puppyName;

    private String puppyType;

    @NotNull
    private Long puppyId;
}
