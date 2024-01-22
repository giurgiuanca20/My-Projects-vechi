package com.example.proj.Controller;

import com.example.proj.Model.Connect;
import com.example.proj.Model.Today;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodayController {
    Connect c = Connect.getInstance();
    private  String dayOfWeek;
    private  String dayOfMonth;

    @PostMapping("/saveData")
    public ResponseEntity<String> saveData(@RequestBody List<Today> todayList) {

        for (Today today : todayList) {
            c.addToday(today);
        }

        return ResponseEntity.ok("Datele au fost salvate cu succes!");
    }

    @PostMapping("/saveDayOfWeek")
    public ResponseEntity<String> saveDayOfWeek(@RequestBody String dayOfWeekData) {
        dayOfWeek = dayOfWeekData.replaceAll("^\"|\"$", "");
        return ResponseEntity.ok("Ziua săptămânii a fost salvată cu succes!");
    }
    @PostMapping("/saveDayOfMonth")
    public ResponseEntity<String> saveDayOfMonth(@RequestBody String dayOfMonthData) {

        dayOfMonth = dayOfMonthData;
        return ResponseEntity.ok("Ziua lunii a fost salvată cu succes!");
    }

    @GetMapping("/getTodayData/{idAccount}")
    public ResponseEntity<List<Today>> getTodayDataByAccountId() {
        List<Today> todayData =c.getTodayData();
        List<Today> weeklyData =c.getTodayDataFromWeekly(dayOfWeek);
        todayData.addAll(weeklyData);
        List<Today> monthlyData =c.getTodayDataFromMonthly(dayOfMonth);
        todayData.addAll(monthlyData);
        return ResponseEntity.ok(todayData);
    }

    @PostMapping("/deleteData")
    public ResponseEntity<String> deleteData(@RequestBody List<Today> todayList) {

        for (Today today : todayList) {
            c.deleteToday(today);
        }
        return ResponseEntity.ok("Datele au fost sterse cu succes!");
    }


}
