package jorados.pupfit.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ResponseWalkedNote<T>{
    private List<T> data;
    private boolean status;

    public ResponseWalkedNote(List<T> data, boolean status) {
        this.data = data;
        this.status = status;
    }
}

