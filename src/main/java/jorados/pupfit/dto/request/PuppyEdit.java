package jorados.pupfit.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class PuppyEdit {

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
