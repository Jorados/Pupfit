package jorados.pupfit.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jorados.pupfit.entity.Puppy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PuppyDto {

    private Long id;

    @NotBlank
    private String puppyName;

//    @NotBlank
    private String puppyType;

//    @NotBlank
    private String imgUrl;

//    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime walkLow;

//    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime walkHigh;

//    @NotNull
    private String personality;

}
