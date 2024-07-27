package jorados.pupfit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jorados.pupfit.entity.Puppy;
import jorados.pupfit.entity.User;
import jorados.pupfit.entity.UserPuppy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalkedNoteDto {

    private Long id;

//    @NotBlank
    private Date walkedDate;

//    @NotBlank
    private String walkedContent;

//    @NotBlank
    private boolean walked;

//    @NotNull
    private Long userPuppyId;
}
