package savenow.backend.entity.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 * 접근 권한 설정을 위한 Enum 타입
 * 현재 User role 만 설정 해 둠
 * 게시판에 이웃 접근에 대한 권한 등을 설정할 때 추가 될 예정
 */
@AllArgsConstructor
@Getter
public enum Role {
    USER("사용자");

    private  String value;
}
