package savenow.backend.dto.daily;

import lombok.Getter;
import lombok.Setter;

public class DailyReqDto {

    @Getter @Setter
    public static class GetDailyReq {
        private String year;
        private String Month;
        private Long userId;
    }
}
