package savenow.backend.entity.history;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HistoryEnum {
    INCOME("수입"), EXPENSE("지출");

    private String value;
}
