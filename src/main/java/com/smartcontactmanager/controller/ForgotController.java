package com.smartcontactmanager.controller;

import com.smartcontactmanager.helper.Message;
import com.smartcontactmanager.service.EmailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

@Controller
public class ForgotController {
    @Autowired
    private EmailService emailService;
    Random random = new Random(1000);

    //email id form open handler
    @GetMapping("/forgot")
    public String openEmailForm() {
        return "forgot_email_form";
    }

    @PostMapping("/send-otp")
    public String sendOTP(@RequestParam("email") String email, HttpSession session) {

        /*generating 4 digits OTP*/

        int otp = random.nextInt(99999);

        /*Code for send otp to email*/

        String subject = "OTP From SCM";
        String message = "OTP=" + otp + "";
        String to = email;

        boolean flag = emailService.sendEmail(subject, message, to);

        if (flag) {
            session.setAttribute("otp", otp);
            return "verify_otp";
        } else {
            session.setAttribute("message", new Message("Check your email id !!", ""));

            return "forgot_email_form";
        }
    }
}