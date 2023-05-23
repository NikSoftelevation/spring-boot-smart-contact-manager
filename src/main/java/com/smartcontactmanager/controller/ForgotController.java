package com.smartcontactmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

@Controller
public class ForgotController {
    Random random = new Random(1000);

    //email id form open handler
    @GetMapping("/forgot")
    public String openEmailForm() {
        return "forgot_email_form";
    }

    @PostMapping("/send-otp")
    public String sendOTP(@RequestParam("email") String email) {

        /*generating 4 digits OTP*/

        int otp = random.nextInt(99999);

        return "verify_otp";
    }
}
