package com.example.proj.Controller;

import com.example.proj.Model.AccountRequest;
import com.example.proj.Model.Connect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogInController {
    Connect c = Connect.getInstance();

    @GetMapping("/logIn")
    public String showLogInPage() {

        return "logIn";
    }
    @RequestMapping("/choice")
    public String showChoicePage() {
        return "choice";
    }
    @RequestMapping("/today")
    public String showTodayPage() {
        return "today";}
    @RequestMapping("/weekly")
    public String showWeeklyPage() {
        return "weekly";
    }
    @RequestMapping("/monthly")
    public String showMonthlyPage() {
        return "monthly";
    }
    @RequestMapping("/annual")
    public String showAnnualPage() {
        return "annual";
    }
    @PostMapping("/verificationAccount")    //verifica da exista account
    public ResponseEntity<Boolean> verificationAccount(@RequestBody AccountRequest accountRequest) {
        int validAccount = c.getIdAccount(accountRequest);
        if (validAccount == -1) {
            return ResponseEntity.ok(false);
        } else {
            return ResponseEntity.ok(true);
        }
    }
    @GetMapping("/getIdAccountValue")   //returneaza id account conectat
    public ResponseEntity<Integer> getIdAccountValue() {
        int accountId = c.getId();// obțineți valoarea din metoda corespunzătoare
        return ResponseEntity.ok(accountId);
    }

}
