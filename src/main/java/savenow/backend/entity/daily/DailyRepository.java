package savenow.backend.entity.daily;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface DailyRepository extends JpaRepository<Daily, Long> {
    Optional<Daily> findByUser_IdAndDate(Long userId, LocalDate date);
    List<Daily> findByUser_IdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

}