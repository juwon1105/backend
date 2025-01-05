package savenow.backend.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
// 응답 DTO
public class ResponseDto<T> {
    private final Integer code; // 1 성공 -1 실패
    private final String msg;
    private final T data;
}
