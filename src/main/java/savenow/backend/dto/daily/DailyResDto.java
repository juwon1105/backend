package savenow.backend.dto.daily;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class DailyResDto {

    @Getter @Setter
    public static class DailyData {
        private String date;
        private Long income;
        private Long expense;

        public DailyData(String date, Long income, Long expense) {
            this.date = date;
            this.income = income;
            this.expense = expense;
        }
    }

    @Getter @Setter
    public static class DailyIncomeData {
        private String date;
        private Long amount;

        public DailyIncomeData(String date, Long amount) {
            this.date = date;
            this.amount = amount;
        }
    }

    @Getter @Setter
    public static class DailyExpenseData{
        private String date;
        private Long amount;

        public DailyExpenseData(String date, Long amount) {
            this.date = date;
            this.amount = amount;
        }
    }

    @Getter @Setter
    public static class MonthlyData {
        private Map<String, DailyData> days;

        public MonthlyData(Map<String, DailyData> days) {
            this.days = days;
        }
    }

    @Getter @Setter
    public static class MonthlyIncomeData {
        private Map<String, DailyIncomeData> days;

        public MonthlyIncomeData(Map<String, DailyIncomeData> days) {
            this.days = days;
        }
    }

    @Getter @Setter
    public static class MonthlyExpenseData {
        private Map<String, DailyExpenseData> days;

        public MonthlyExpenseData(Map<String, DailyExpenseData> days) {
            this.days = days;
        }
    }
}
