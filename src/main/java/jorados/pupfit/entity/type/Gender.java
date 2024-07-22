package jorados.pupfit.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum Gender {
    MALE("남"), FEMALE("여");
    private final String value;

    private Gender(String value){
        this.value = value;
    }
}
