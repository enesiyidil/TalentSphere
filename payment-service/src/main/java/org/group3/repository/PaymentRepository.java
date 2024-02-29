package org.group3.repository;

import org.group3.entity.Payment;
import org.group3.entity.enums.EStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends ElasticsearchRepository<Payment, String> {


    List<Payment> findAllByCompanyId(Long companyId);

    List<Payment> findAllByStatusEqualsAndCompanyId(EStatus eStatus, Long companyId);

}
