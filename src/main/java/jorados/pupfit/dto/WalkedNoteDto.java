package jorados.pupfit.dto;

import jakarta.validation.constraints.NotBlank;
import jorados.pupfit.entity.Puppy;
import jorados.pupfit.entity.User;
import jorados.pupfit.entity.UserPuppy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class WalkedNoteDto {

    private Long id;

    @NotBlank
    private Date walkedDate;

    @NotBlank
    private String walkedContent;

    @NotBlank
    private boolean walked;

    private UserPuppy userPuppy;
}
