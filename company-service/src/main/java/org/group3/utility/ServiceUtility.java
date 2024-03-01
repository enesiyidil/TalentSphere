package org.group3.utility;

import org.group3.entity.IStatus;
import org.group3.entity.enums.EStatus;
import org.group3.exception.CompanyServiceException;
import org.group3.exception.ErrorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceUtility {

    public <ENTITY extends IStatus, REPOSITORY extends JpaRepository<ENTITY, Long>> Boolean softDelete(Long id, REPOSITORY repository){
        Optional<ENTITY> optionalEntity = repository.findById(id);
        if (optionalEntity.isPresent()) {
            if (optionalEntity.get().getStatus() == EStatus.DELETED)
                throw new CompanyServiceException(ErrorType.COMPANY_NOT_ACTIVE);
            optionalEntity.get().setStatus(EStatus.DELETED);
            repository.save(optionalEntity.get());
            return true;
        }
        throw new CompanyServiceException(ErrorType.COMPANY_NOT_FOUND);
    }
}
