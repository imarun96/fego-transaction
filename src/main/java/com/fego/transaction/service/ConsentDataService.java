package com.fego.transaction.service;

import com.fego.transaction.common.base.BaseMapper;
import com.fego.transaction.common.base.BaseService;
import com.fego.transaction.common.base.BaseTask;
import com.fego.transaction.common.base.specification.IdSpecifications;
import com.fego.transaction.common.utils.CacheDataUtil;
import com.fego.transaction.dto.ConsentDataDto;
import com.fego.transaction.entity.Consent;
import com.fego.transaction.repository.ConsentRepository;
import com.fego.transaction.task.ConsentTask;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Arun Balaji Rajasekaran
 */

@Service
public class ConsentDataService extends BaseService<Consent, ConsentDataDto, ConsentTask> {

    public ConsentDataService(BaseMapper<Consent, ConsentDataDto> consentMapper,
                              IdSpecifications<Consent> userIdSpecifications, ConsentRepository consentRepository,
                              BaseTask<Consent> consentTask) {
        super(consentRepository, consentMapper, userIdSpecifications, consentTask);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateAdd(ConsentDataDto consentDto) {
        consentDto.setUserId(CacheDataUtil.getUserId());
        consentDto.setCreatedBy(CacheDataUtil.getUserId());
        consentDto.setUpdatedBy(CacheDataUtil.getUserId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validatePatch(ConsentDataDto incomingDto) {
        incomingDto.setUpdatedBy(CacheDataUtil.getUserId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doPatch(Consent incomingConsent, Consent toUpdateConsent) {
        if (Objects.nonNull(incomingConsent.getConsentId())) {
            toUpdateConsent.setConsentId(incomingConsent.getConsentId());
        }
        if (Objects.nonNull(incomingConsent.getCustomerVua())) {
            toUpdateConsent.setCustomerVua(incomingConsent.getCustomerVua());
        }
        if (Objects.nonNull(incomingConsent.getConsentStart())) {
            toUpdateConsent.setConsentStart(incomingConsent.getConsentStart());
        }
        if (Objects.nonNull(incomingConsent.getConsentExpiry())) {
            toUpdateConsent.setConsentExpiry(incomingConsent.getConsentExpiry());
        }
        if (Objects.nonNull(incomingConsent.getFrequencyUnit())) {
            toUpdateConsent.setFrequencyUnit(incomingConsent.getFrequencyUnit());
        }
        if (Objects.nonNull(incomingConsent.getFrequencyValue())) {
            toUpdateConsent.setFrequencyValue(incomingConsent.getFrequencyValue());
        }
        if (Objects.nonNull(incomingConsent.getFiDataFrom())) {
            toUpdateConsent.setFiDataFrom(incomingConsent.getFiDataFrom());
        }
        if (Objects.nonNull(incomingConsent.getFiDataTo())) {
            toUpdateConsent.setFiDataTo(incomingConsent.getFiDataTo());
        }
    }
}