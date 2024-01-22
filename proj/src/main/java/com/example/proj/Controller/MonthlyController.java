package com.example.proj.Controller;

import com.example.proj.Model.Color;
import com.example.proj.Model.Connect;
import com.example.proj.Model.Monthly;
import com.example.proj.Model.Today;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class MonthlyController {

private int number;

    Connect c = Connect.getInstance();

    @PostMapping("/saveSquare")
    public ResponseEntity<String> squareNumber(@RequestBody int numberAsString) {
        number = numberAsString;
        return ResponseEntity.ok("Success");
    }

    @RequestMapping("/addMonthly")
    public String showChoicePage() {
        return "addMonthly";
    }

    @GetMapping("/number")
    public ResponseEntity<Integer> getnumber() {
        return ResponseEntity.ok(number);
    }

    @PostMapping("/saveMonthly")
    public ResponseEntity<String> saveMonthly(@RequestBody List<Monthly> monthlyList) {
        for (Monthly monthly : monthlyList) {
            c.addMonthly(monthly);
        }
        return ResponseEntity.ok("Datele au fost salvate cu succes!");
    }

    @PostMapping("/saveColor")
    public ResponseEntity<String> saveColor(@RequestBody Color color) {
        c.addColor(color);
        return ResponseEntity.ok("Datele au fost salvate cu succes!");
    }

    @GetMapping("/getColorList")
    public ResponseEntity<List<Color>> getColorList() {
        List<Color> colorData = c.getColorList();
        return ResponseEntity.ok(colorData);
    }

    @GetMapping("getMonthlyData")
    public ResponseEntity<List<Today>> getMonthlyData() {
        List<Today> monthlyData = c.getMonthlyData(number);
        return ResponseEntity.ok(monthlyData);
    }



    @PostMapping("/deleteMonthly")
    public ResponseEntity<String> deleteData(@RequestBody List<Monthly> monthlyList) {

        for (Monthly monthly : monthlyList) {
            c.deleteMonthly(monthly);
        }
        return ResponseEntity.ok("Datele au fost sterse cu succes!");
    }

}
