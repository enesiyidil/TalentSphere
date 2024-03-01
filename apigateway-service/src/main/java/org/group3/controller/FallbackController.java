package org.group3.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/auth")
    public ResponseEntity<String> fallbackAuth(){
        return ResponseEntity.ok("Auth Service out of service.");
    }

    @GetMapping("/manager")
    public ResponseEntity<String> fallbackManager(){
        return ResponseEntity.ok("Manager Service out of service.");
    }

    @GetMapping("/personal")
    public ResponseEntity<String> fallbackPersonal(){
        return ResponseEntity.ok("Personal Service out of service.");
    }
    @GetMapping("/visitor")
    public ResponseEntity<String> fallbackVisitor(){
        return ResponseEntity.ok("Visitor Service out of service.");
    }
    @GetMapping("/company")
    public ResponseEntity<String> fallbackCompany(){
        return ResponseEntity.ok("Company Service out of service.");
    }
    @GetMapping("/shift")
    public ResponseEntity<String> fallbackShift(){
        return ResponseEntity.ok("Shift Service out of service.");
    }
    @GetMapping("/communication")
    public ResponseEntity<String> fallbackCommunication(){
        return ResponseEntity.ok("Communication Service out of service.");
    }

    @GetMapping("/break")
    public ResponseEntity<String> fallbackBreak(){
        return ResponseEntity.ok("Break Service out of service.");
    }

    @GetMapping("/payment")
    public ResponseEntity<String> fallbackPayment(){
        return ResponseEntity.ok("Payment Service out of service.");
    }

    @GetMapping("/holiday")
    public ResponseEntity<String> fallbackHoliday(){
        return ResponseEntity.ok("Holiday Service out of service.");
    }
    @GetMapping("/comment")
    public ResponseEntity<String> fallbackComment(){
        return ResponseEntity.ok("Comment Service out of service.");
    }


}
