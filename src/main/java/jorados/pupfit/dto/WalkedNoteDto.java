package jorados.pupfit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalkedNoteDto {

    private Long id;

//    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime walkedDate; // 산책 시간 ex) 2024-07-27 10:30:00

//    @NotBlank
    private String walkedContent;

//    @NotBlank
    private boolean walked;

//    @NotNull
    private Long userPuppyId;
}
