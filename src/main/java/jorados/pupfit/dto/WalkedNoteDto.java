package jorados.pupfit.dto;

import jakarta.validation.constraints.NotBlank;
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
}
