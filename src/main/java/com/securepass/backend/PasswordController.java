package com.securepass.backend;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/password")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000", "https://*.vercel.app"}, originPatterns = {"https://*.vercel.app", "http://localhost:*"})
public class PasswordController {

    @PostMapping("/analyze")
    public Map<String, Object> analyzePassword(
            @RequestBody Map<String, String> request) {

        String password = request.getOrDefault("password", "");

        int score = 0;
        List<String> checks = new ArrayList<>();

        boolean length = password.length() >= 8;
        boolean uppercase = password.matches(".*[A-Z].*");
        boolean lowercase = password.matches(".*[a-z].*");
        boolean number = password.matches(".*[0-9].*");
        boolean special = password.matches(".*[^A-Za-z0-9].*");

        if (length) score += 25;
        if (uppercase) score += 20;
        if (lowercase) score += 20;
        if (number) score += 20;
        if (special) score += 15;

        checks.add(length ? "At least 8 characters" : "Use at least 8 characters");
        checks.add(uppercase ? "Uppercase letter included" : "Add an uppercase letter");
        checks.add(lowercase ? "Lowercase letter included" : "Add a lowercase letter");
        checks.add(number ? "Number included" : "Add a number");
        checks.add(special ? "Special character included" : "Add a special character");

        String strength;
        if (score < 40) {
            strength = "Weak";
        } else if (score < 70) {
            strength = "Medium";
        } else {
            strength = "Strong";
        }

        Map<String, Object> response = new HashMap<>();
        response.put("score", score);
        response.put("strength", strength);
        response.put("checks", checks);

        return response;
    }
}