package org.group3.controller;

import lombok.RequiredArgsConstructor;
import org.group3.dto.request.PaymentRequestDto;
import org.group3.entity.Payment;
import org.group3.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.group3.constant.EndPoints.*;

@CrossOrigin(maxAge = 3600, allowedHeaders = "*")
@RestController
@RequestMapping(PAYMENT)
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping(SAVE)
    public ResponseEntity<Payment> save(@RequestBody PaymentRequestDto dto){
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping(FIND_BY_ID)
    public ResponseEntity<Payment> findById(@RequestParam String id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<Iterable<Payment>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping(DELETE + "/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable String id){
        return ResponseEntity.ok(service.deleteById(id));
    }

    @PatchMapping(UPDATE)
    public ResponseEntity<Payment> update(@RequestBody PaymentRequestDto dto){
        return ResponseEntity.ok(service.update(dto));
    }

    @GetMapping(GET_PAYMENTS_BY_DUE_DATE_RANGE)
    public  ResponseEntity<List<Payment>> getPaymentsByDueDateRange(@RequestParam Long companyId, @RequestParam Long startTime, @RequestParam Long endTime){
        return ResponseEntity.ok(service.getPaymentsByDueDateRange(companyId, startTime, endTime));
    }

    @GetMapping(GET_PAYMENTS_BY_PAYMENT_RANGE)
    public  ResponseEntity<List<Payment>> getPaymentsByPaymentRange(@RequestParam Long companyId, @RequestParam Long startTime, @RequestParam Long endTime){
        return ResponseEntity.ok(service.getPaymentsByPaymentRange(companyId, startTime, endTime));
    }

    @GetMapping(GET_PAYMENTS_BY_CREATED_DATE_RANGE)
    public  ResponseEntity<List<Payment>> getPaymentsByCreatedDateRange(@RequestParam Long companyId, @RequestParam Long startTime, @RequestParam Long endTime){
        return ResponseEntity.ok(service.getPaymentsByCreatedDateRange(companyId, startTime, endTime));
    }

    @GetMapping(FIND_ALL_BY_COMPANY_ID)
    public  ResponseEntity<List<Payment>> findAllByCompanyId(@RequestParam Long companyId){
        return ResponseEntity.ok(service.findAllByCompanyId(companyId));
    }

    @GetMapping(COMPLETE_PAYMENT)
    public  ResponseEntity<Payment> completePayment(@RequestParam String id){
        return ResponseEntity.ok(service.completePayment(id));
    }
}
