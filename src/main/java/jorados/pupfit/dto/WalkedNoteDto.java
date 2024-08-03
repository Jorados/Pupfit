package jorados.pupfit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalkedNoteDto {

    private Long id;

//    @NotBlank
    private LocalDateTime walkedDate; // 산책 시간 ex) 2024-07-27 10:30:00

//    @NotBlank
    private String walkedContent;

//    @NotBlank
    private boolean walked;

//    @NotNull
    private Long userPuppyId;

    private String puppyPersonalName;
    private String puppyName;
}
