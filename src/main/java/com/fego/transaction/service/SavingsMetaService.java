package com.fego.transaction.service;

import com.fego.transaction.common.base.BaseMapper;
import com.fego.transaction.common.base.BaseService;
import com.fego.transaction.common.base.BaseTask;
import com.fego.transaction.common.base.specification.IdSpecifications;
import com.fego.transaction.dto.SavingsMetaDto;
import com.fego.transaction.entity.SavingsMeta;
import com.fego.transaction.repository.SavingsMetaRepository;
import com.fego.transaction.task.SavingsMetaTask;
import org.springframework.stereotype.Service;

/**
 * @author Arun Balaji Rajasekaran
 */

@Service
public class SavingsMetaService extends BaseService<SavingsMeta, SavingsMetaDto, SavingsMetaTask> {

    public SavingsMetaService(SavingsMetaRepository savingsMetaRepository,
                              BaseMapper<SavingsMeta, SavingsMetaDto> savingsMetaMapper,
                              IdSpecifications<SavingsMeta> savingsMetaSpecifications, BaseTask<SavingsMeta> savingsMetaTask) {
        super(savingsMetaRepository, savingsMetaMapper, savingsMetaSpecifications, savingsMetaTask);
    }
}