package savenow.backend.entity.daily;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import savenow.backend.entity.user.User;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "daily_tb")
public class Daily {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Long income = 0L;

    @Column(nullable = false)
    private Long expense = 0L;

    @Column(nullable = false)
    private String feedback;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Daily(Long id, LocalDate date, Long income, Long expense, String feedback, User user) {
        this.id = id;
        this.date = date;
        this.income = income;
        this.expense = expense;
        this.feedback = feedback;
        this.user = user;
    }

    public void update(Long income, Long expense, String feedback) {
        this.income = income;
        this.expense = expense;
        this.feedback = feedback;
    }
}
