package savenow.backend.entity.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import savenow.backend.entity.daily.Daily;
import savenow.backend.entity.history.History;

import java.util.ArrayList;
import java.util.List;

/*
 * 유저 테이블
 * id, 이메일, 비밀번호, 이름, 성별
 * 구현 완료 확인 후 프로필 사진 추가 예정
 */

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_tb")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String username; // 유저 이름 (닉네임)

    @Column(unique = true,nullable = false)
    private String email; // 유저 이메일

    @Column(nullable = false)
    private String password; // 유저 비밀번호

    @Column(nullable = false)
    private String birth; // 유저 생년월일 (19020202 형식)

    @Enumerated
    private Gender gender; // 유저 성별

    @Enumerated
    @Column(nullable = false)
    private Role role;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<History> histories = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Daily> dailyList = new ArrayList<>();

    @Builder
    public User(Long id, String username, String email, String password, String birth, Gender gender, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.birth = birth;
        this.gender = gender;
        this.role = role;
    }



    public void addHistory(History history) {
        this.histories.add(history);
        history.setUser(this);
    }

    public void addDaily(Daily daily) {
        this.dailyList.add(daily);
        daily.setUser(this);
    }
}

