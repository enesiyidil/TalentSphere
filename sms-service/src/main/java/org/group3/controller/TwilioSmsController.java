package org.group3.controller;

import lombok.RequiredArgsConstructor;
import org.group3.service.TwilioSmsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/twilio")
@RequiredArgsConstructor
public class TwilioSmsController {

    private final TwilioSmsService service;

    @GetMapping
    public void sendSmsTest(String to, String messageBody){
        service.sendSms(to, messageBody);
    }
}
