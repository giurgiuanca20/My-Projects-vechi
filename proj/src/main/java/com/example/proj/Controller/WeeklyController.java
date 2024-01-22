package com.example.proj.Controller;

import com.example.proj.Model.Connect;
import com.example.proj.Model.Weekly;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class WeeklyController {

    Connect c = Connect.getInstance();

    @PostMapping("/addWeeklyData")
    public ResponseEntity<String> addWeeklyData(@RequestBody Weekly weekly) {
        c.addWeekly(weekly);
        return ResponseEntity.ok("Datele au fost adăugate cu succes în baza de date.");
    }

    @RequestMapping("/showWeekly")
    public String showChoicePage() {
        return "showWeekly";
    }

    @GetMapping("/getTableData")
    public ResponseEntity<List<Weekly>> getTableData() {
        List<Weekly> weeklyData = c.getWeeklyData();
        return ResponseEntity.ok(weeklyData);
    }

    @PostMapping("/deleteCell")
    public ResponseEntity<String> deleteData(@RequestBody List<Weekly> weeklies) {
        for (Weekly weekly : weeklies) {
                c.deleteWeekly(weekly);
        }
        return ResponseEntity.ok("Datele au fost sterse cu succes!");
    }
}
