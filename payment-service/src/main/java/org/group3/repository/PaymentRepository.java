package org.group3.repository;

import org.group3.entity.Payment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends ElasticsearchRepository<Payment, String> {


}
