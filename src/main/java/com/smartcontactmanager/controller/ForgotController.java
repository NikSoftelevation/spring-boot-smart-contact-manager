package com.smartcontactmanager.controller;

import com.smartcontactmanager.domain.User;
import com.smartcontactmanager.helper.Message;
import com.smartcontactmanager.repository.UserRepository;
import com.smartcontactmanager.service.EmailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

@Controller
public class ForgotController {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;
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
        String message = ""
                + "<div style='border:1px solid #e2e2e2;padding:20px'>"
                + "<h1>"
                + "OTP is "
                + "<b>" + otp
                + "</b>"
                + "</h1>"
                + "</div>";
        String to = email;

        boolean flag = emailService.sendEmail(subject, message, to);

        if (flag) {
            session.setAttribute("myotp", otp);
            session.setAttribute("email", email);
            return "verify_otp";
        } else {
            session.setAttribute("message", new Message("Check your email id !!", ""));

            return "forgot_email_form";
        }
    }

    /*Verify Opt*/
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam("otp") Integer otp, HttpSession session) {

        Integer myotp = (int) session.getAttribute("myotp");
        String email = (String) session.getAttribute("email");

        if (myotp == otp) {

            /*Password change form*/
            User user = userRepository.getUserByUserName(email);
            if (user == null) {

                /*send error message*/
                session.setAttribute("message", new Message("User does not exist with this email !! ", "danger"));

                return "forgot_email_form";
            } else {

                /*send change password form*/
            }

            return "password_change_form";
        } else {

            session.setAttribute("message", new Message("You have entered wrong otp!!", "danger"));
            return "verify-otp";
        }
    }

    /*Change password*/
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("newpassword") String newpassword, HttpSession session) {
        String email = (String) session.getAttribute("email");
        User user = userRepository.getUserByUserName(email);
        user.setPassword(bCryptPasswordEncoder.encode(newpassword));
        userRepository.save(user);

        return "redirect:/signIn?change=password changed successfully";
    }
}