package com.example.proj.Controller;

import com.example.proj.Model.Account;
import com.example.proj.Model.Connect;
import com.example.proj.Model.Today;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SignUpController {

    Connect c = Connect.getInstance();

    @RequestMapping("/signUp")
    public String showSignUpPage() {
        return "signUp";
    }

    @RequestMapping("/successAccount")
    public String showSuccessPage() {
        return "successAccount";
    }

    @PostMapping("/signUp")
    public String signUp(@RequestParam String username,
                                         @RequestParam String password,
                                         @RequestParam String firstName,
                                         @RequestParam String lastName,
                                         @RequestParam String phoneNumber,
                                         @RequestParam String birthDate,
                                         @RequestParam String email) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setPhoneNumber(phoneNumber);
        account.setBirthDate(birthDate);
        account.setEmail(email);
        // Validează parola
//        if (account.getPassword() != null && !validatePassword(account.getPassword())) {
//            bindingResult.rejectValue("password", "error.password", "Parola trebuie să conțină caractere speciale, litere mari, cifre și să aibă minim 8 caractere.");
//        }

        c.addInfoAccount(account);
        showSuccessPage();

        return "redirect:/successAccount";
    }


    private boolean validatePassword(String password) {
        String regex = "^(?=.*[!@#$%^&*])(?=.*[A-Z])(?=.*[0-9]).{8,}$";
        return password.matches(regex);
    }
}

