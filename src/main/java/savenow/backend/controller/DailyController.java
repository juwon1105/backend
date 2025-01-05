package savenow.backend.controller;

import java.time.format.DateTimeFormatter;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import savenow.backend.dto.daily.DailyReqDto.GetDailyReq;
import savenow.backend.dto.daily.DailyResDto.MonthlyData;
import savenow.backend.dto.daily.DailyResDto.MonthlyExpenseData;
import savenow.backend.dto.daily.DailyResDto.MonthlyIncomeData;
import savenow.backend.entity.daily.Daily;
import savenow.backend.service.DailyService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DailyController {

    private final DailyService dailyService;

    @GetMapping("/account")
    public ResponseEntity<?> getMonthlyData(@RequestParam int year, @RequestParam int month, @RequestParam Long userId) {
        Map<String, Map<String, Long>> dailyData = dailyService.getMonthlyAccountData(year, month, userId);
        return ResponseEntity.ok(Map.of(
                "year", year,
                "month", month,
                "daily_data", dailyData
        ));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addFeedback(@RequestBody Map<String, Object> body) {
        Long userId = Long.valueOf(body.get("userId").toString());
        LocalDate date = LocalDate.parse(body.get("date").toString());
        Long income = Long.valueOf(body.get("income").toString());
        Long expense = Long.valueOf(body.get("expense").toString());
        String feedback = body.get("feedback").toString();

        Daily daily = dailyService.addFeedback(userId, date, income, expense, feedback);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "피드백이 성공적으로 추가되었습니다."
        ));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateFeedback(@RequestBody Map<String, Object> body) {
        Long userId = Long.valueOf(body.get("userId").toString());
        LocalDate date = LocalDate.parse(body.get("date").toString());
        Long income = Long.valueOf(body.get("income").toString());
        Long expense = Long.valueOf(body.get("expense").toString());
        String feedback = body.get("feedback").toString();

        Daily daily = dailyService.updateFeedback(userId, date, income, expense, feedback);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "피드백이 성공적으로 수정되었습니다."
        ));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFeedback(@RequestBody Map<String, Object> body) {
        Long userId = Long.valueOf(body.get("userId").toString());
        LocalDate date = LocalDate.parse(body.get("date").toString());

        dailyService.deleteFeedback(userId, date);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "피드백이 성공적으로 삭제되었습니다."
        ));
    }
}
