package savenow.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import savenow.backend.dto.daily.DailyReqDto.GetDailyReq;
import savenow.backend.dto.daily.DailyResDto.*;
import savenow.backend.entity.daily.Daily;
import savenow.backend.entity.daily.DailyRepository;
import savenow.backend.entity.user.User;
import savenow.backend.entity.user.UserRepository;
import savenow.backend.handler.exception.CustomApiException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class DailyService {

    private final DailyRepository dailyRepository;
    private final UserRepository userRepository;

    @Transactional
    public Daily addFeedback(Long userId, LocalDate date, Long income, Long expense, String feedback) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Daily daily = dailyRepository.findByUser_IdAndDate(userId, date).orElse(
                new Daily(null, date, 0L, 0L, "", user));
        daily.update(daily.getIncome() + income, daily.getExpense() + expense, feedback);
        return dailyRepository.save(daily);
    }

    @Transactional
    public Daily updateFeedback(Long userId, LocalDate date, Long income, Long expense, String feedback) {
        Daily daily = dailyRepository.findByUser_IdAndDate(userId, date)
                .orElseThrow(() -> new IllegalArgumentException("해당 날짜의 데이터가 없습니다."));
        daily.update(income, expense, feedback);
        return dailyRepository.save(daily);
    }

    @Transactional
    public void deleteFeedback(Long userId, LocalDate date) {
        Daily daily = dailyRepository.findByUser_IdAndDate(userId, date)
                .orElseThrow(() -> new IllegalArgumentException("해당 날짜의 데이터가 없습니다."));
        dailyRepository.delete(daily);
    }
    @Transactional(readOnly = true)
    public Map<String, Map<String, Long>> getMonthlyAccountData(int year, int month, Long userId) {
        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 해당 월의 데이터 조회
        List<Daily> dailyList = dailyRepository.findByUser_IdAndDateBetween(
                userId,
                LocalDate.of(year, month, 1),
                LocalDate.of(year, month, LocalDate.of(year, month, 1).lengthOfMonth())
        );

        // 데이터를 Map으로 변환
        Map<String, Map<String, Long>> dailyDataMap = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Daily daily : dailyList) {
            String date = daily.getDate().format(formatter);
            Map<String, Long> incomeExpenseMap = new HashMap<>();
            incomeExpenseMap.put("income", daily.getIncome());
            incomeExpenseMap.put("expense", daily.getExpense());
            dailyDataMap.put(date, incomeExpenseMap);
        }

        return dailyDataMap;
    }
}