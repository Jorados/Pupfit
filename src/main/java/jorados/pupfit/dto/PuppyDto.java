package jorados.pupfit.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jorados.pupfit.entity.Puppy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class PuppyDto {

    private Long id;
    @NotBlank
    private String puppyName;

    @NotBlank
    private String puppyType;

    @NotBlank
    private String imgUrl;

    @NotBlank
    private Date walkLow;

    @NotBlank
    private Date walkHigh;

    @NotNull
    private String personality;

}
