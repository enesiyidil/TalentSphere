package org.group3.controller;

import lombok.RequiredArgsConstructor;
import org.group3.dto.request.PaymentRequestDto;
import org.group3.entity.Payment;
import org.group3.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping("/save")
    public ResponseEntity<Payment> save(@RequestBody PaymentRequestDto dto){
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping("/findById")
    public ResponseEntity<Payment> findById(@RequestParam String id){
        return ResponseEntity.ok(service.findById(id));
    }
    @GetMapping("/findAll")
    public ResponseEntity<Iterable<Payment>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }
    @DeleteMapping("/deleteById")
    public ResponseEntity<Boolean> deleteById(@RequestParam String id){
        return ResponseEntity.ok(service.deleteById(id));
    }
    @PatchMapping("/update")
    public ResponseEntity<Payment> update(@RequestParam String id, @RequestBody PaymentRequestDto dto){
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("/getPaymentsByDueDateRange")
    public  ResponseEntity<List<Payment>> getPaymentsByDueDateRange(@RequestParam Long companyId, @RequestParam Long startTime, @RequestParam Long endTime){
        return ResponseEntity.ok(service.getPaymentsByDueDateRange(companyId, startTime, endTime));
    }

    @GetMapping("/getPaymentsByPaymentRange")
    public  ResponseEntity<List<Payment>> getPaymentsByPaymentRange(@RequestParam Long companyId, @RequestParam Long startTime, @RequestParam Long endTime){
        return ResponseEntity.ok(service.getPaymentsByPaymentRange(companyId, startTime, endTime));
    }

    @GetMapping("/getPaymentsByCreatedDateRange")
    public  ResponseEntity<List<Payment>> getPaymentsByCreatedDateRange(@RequestParam Long companyId, @RequestParam Long startTime, @RequestParam Long endTime){
        return ResponseEntity.ok(service.getPaymentsByCreatedDateRange(companyId, startTime, endTime));
    }

    @GetMapping("/findAllByCompanyId")
    public  ResponseEntity<List<Payment>> findAllByCompanyId(@RequestParam Long companyId){
        return ResponseEntity.ok(service.findAllByCompanyId(companyId));
    }

    @GetMapping("/completePayment")
    public  ResponseEntity<Payment> completePayment(@RequestParam String id){
        return ResponseEntity.ok(service.completePayment(id));
    }
}
