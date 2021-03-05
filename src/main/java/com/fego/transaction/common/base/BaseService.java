package com.fego.transaction.common.base;

import com.fego.transaction.common.base.specification.IdSpecifications;
import com.fego.transaction.common.config.PagingConfig;
import com.fego.transaction.common.config.ResponseMessage;
import com.fego.transaction.common.constants.Constants;
import com.fego.transaction.common.enumeration.ErrorCode;
import com.fego.transaction.common.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class BaseService<M extends BaseModel, D extends BaseDto, T extends BaseTask> {

    BaseRepository<M> baseRepository;
    IdSpecifications<M> idSpecifications;
    BaseMapper<M, D> baseMapper;
    BaseTask<M> baseTask;
    Class<M> modelType;
    @Autowired
    private ResponseMessage responseMessage;
    @Autowired
    private PagingConfig pagingConfig;

    protected BaseService(BaseRepository<M> baseRepository, BaseMapper<M, D> baseMapper,
                          IdSpecifications<M> idSpecifications, BaseTask<M> baseTask) {
        this.baseRepository = baseRepository;
        this.idSpecifications = idSpecifications;
        this.baseMapper = baseMapper;
        this.baseTask = baseTask;
        this.modelType = (Class<M>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void validateAdd(D incomingDto) {
    }

    public void preAdd(M incomingModel) {
    }

    public void postAdd(M savedModel) {
    }

    public final D add(D incomingDto) {
        validateAdd(incomingDto);
        M incomingModel = baseMapper.dtoToDomain(incomingDto);
        M savedModel = addModel(incomingModel);
        return baseMapper.domainToDto(savedModel);
    }

    public final M addModel(M incomingModel) {
        M savedModel = baseRepository.save(incomingModel);
        postAdd(savedModel);
        baseTask.onCreate(savedModel);
        return savedModel;
    }

    /**
     * The extending services shall implement their logic to patch the
     * `toUpdateModel` with the `incomingModel`
     * <p>
     * This method should be abstract in which case all extending service classes
     * must provide an implementation.
     *
     * @param incomingModel
     * @param toUpdateModel
     */
    public void doPatch(M incomingModel, M toUpdateModel) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    public void validatePatch(D incomingDto) {
    }

    public final D patch(D incomingDto) {
        Optional<M> toUpdate = baseRepository.findOne(idSpecifications.findById(incomingDto.getId()).and(idSpecifications.notDeleted()));
        if (toUpdate.isEmpty()) {
            throw new RecordNotFoundException(ErrorCode.RECORD_NOT_FOUND, Constants.RECORD_NOT_FOUND);
        }
        M toUpdateModel = toUpdate.get();
        validatePatch(incomingDto);
        incomingDto.setCreatedBy(toUpdateModel.getCreatedBy());
        M incomingModel = baseMapper.dtoToDomain(incomingDto);
        M patchedModel = patchModel(incomingModel, toUpdateModel);
        return baseMapper.domainToDto(patchedModel);
    }

    public final M patchModel(M incomingModel, M toUpdateModel) {
        doPatch(incomingModel, toUpdateModel);
        M patchedModel = baseRepository.saveAndFlush(toUpdateModel);
        baseTask.onUpdate(patchedModel);
        return patchedModel;
    }

    public void validateUpdate(D incomingDto) {
    }

    public void preUpdate(M incomingModel, M toUpdateModel) {
    }

    public void postUpdate(M toUpdateModel, M updatedModel) {
    }

    public final D update(D incomingDto) {
        Optional<M> toUpdate = baseRepository.findOne(idSpecifications.findById(incomingDto.getId()).and(idSpecifications.notDeleted()));
        if (toUpdate.isEmpty()) {
            throw new RecordNotFoundException(ErrorCode.RECORD_NOT_FOUND, Constants.RECORD_NOT_FOUND);
        }
        M toUpdateModel = toUpdate.get();
        validateUpdate(incomingDto);
        incomingDto.setCreatedBy(toUpdateModel.getCreatedBy());
        incomingDto.setCreatedAt(toUpdateModel.getCreatedAt());
        M incomingModel = baseMapper.dtoToDomain(incomingDto);
        incomingModel.setId(toUpdateModel.getId());
        M updatedModel = updateModel(incomingModel, toUpdateModel);
        return baseMapper.domainToDto(updatedModel);
    }

    private final M updateModel(M incomingModel, M toUpdateModel) {
        preUpdate(incomingModel, toUpdateModel);
        M updatedModel = baseRepository.save(incomingModel);
        postUpdate(toUpdateModel, updatedModel);
        baseTask.onUpdate(updatedModel);
        return updatedModel;
    }

    public final M saveModel(M model) {
        return baseRepository.saveAndFlush(model);
    }

    public void preUpdate(M model) {
    }

    public void postUpdate(M model) {
    }

    public final M updateModel(M model) {
        preUpdate(model);
        M updatedModel = saveModel(model);
        postUpdate(updatedModel);
        return updatedModel;
    }

    public D findOne(Specification<M> specs) {
        Optional<M> fegoEntity = findOneModel(specs);
        if (fegoEntity.isEmpty()) {
            throw new RecordNotFoundException(ErrorCode.RECORD_NOT_FOUND, Constants.RECORD_NOT_FOUND);
        }
        return baseMapper.domainToDto(fegoEntity.get());
    }

    public Optional<M> findOneModel(Specification<M> specs) {
        return baseRepository.findOne(specs.and(idSpecifications.notDeleted()));
    }

    public Optional<M> findModelById(Long id) {
        return baseRepository.findById(id);
    }

    public D findByUserId(Long id) {
        Specification<M> baseSpecification = idSpecifications.findByUserId(id);
        Optional<M> fegoEntity = baseRepository.findOne(baseSpecification.and(idSpecifications.notDeleted()));
        if (fegoEntity.isEmpty()) {
            throw new RecordNotFoundException(ErrorCode.RECORD_NOT_FOUND,
                    responseMessage.getErrorMessage(ErrorCode.RECORD_NOT_FOUND.key(),
                            String.format("There is no %s with user id %s", modelType.getSimpleName(), id)));
        }
        return baseMapper.domainToDto(fegoEntity.get());
    }

    public D findById(Long id) {
        Specification<M> baseSpecification = idSpecifications.findById(id);
        Optional<M> fegoEntity = baseRepository.findOne(baseSpecification.and(idSpecifications.notDeleted()));
        if (fegoEntity.isEmpty()) {
            throw new RecordNotFoundException(ErrorCode.RECORD_NOT_FOUND, "Record not found");
        }
        return baseMapper.domainToDto(fegoEntity.get());
    }

    public Page<D> findAll(Integer pageNo, Integer pageSize, String sortBy, Optional<Specification<M>> specifications,
                           String direction) {
        Page<M> pagedModels = findAllModels(pageNo, pageSize, sortBy, specifications, direction);
        return pagedModels.map(m -> baseMapper.domainToDto(m));
    }

    public Page<M> findAllModels(Integer pageNo, Integer pageSize, String sortBy,
                                 Optional<Specification<M>> specifications, String direction) {
        if (pageSize > pagingConfig.getMaximum()) {
            pageSize = pagingConfig.getMaximum();
        }
        Sort sort = Sort.by(sortBy);
        if (direction.equals("ASC"))
            sort = Sort.by(Sort.Direction.ASC, sortBy);
        if (direction.equals("DESC"))
            sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable paging = PageRequest.of(pageNo, pageSize, sort);
        return specifications.map(mSpecification -> baseRepository.findAll(mSpecification.and(idSpecifications.notDeleted()), paging))
                .orElseGet(() -> baseRepository.findAll(paging));
    }

    public List<D> findAll() {
        List<M> models = findAllModels();
        return models.stream().map(m -> baseMapper.domainToDto(m)).collect(Collectors.toList());
    }

    public List<D> findAll(Specification<M> specifications) {
        List<M> models = findAllModels(specifications);
        return models.stream().map(m -> baseMapper.domainToDto(m)).collect(Collectors.toList());
    }

    public List<M> findAllModels(Specification<M> specifications) {
        return baseRepository.findAll(specifications.and(idSpecifications.notDeleted()));
    }

    public List<M> findAllModels() {
        return baseRepository.findAll();
    }

    public void validateDeleteByGid(long gid) {
    }

    public void preDeleteByGid(M toDeleteModel) {
    }

    public void postDeleteByGid(M deletedModel) {
    }

    public final D deleteById(long id) {
        Specification<M> baseSpecification = idSpecifications.findById(id);
        Optional<M> toDelete = baseRepository.findOne(baseSpecification.and(idSpecifications.notDeleted()));
        if (toDelete.isEmpty()) {
            throw new RecordNotFoundException(ErrorCode.RECORD_NOT_FOUND, "Record not found");
        }
        validateDeleteByGid(id);
        M toDeleteModel = toDelete.get();
        preDeleteByGid(toDeleteModel);
        baseRepository.delete(toDeleteModel);
        postDeleteByGid(toDeleteModel);
        baseTask.onDelete(toDeleteModel);
        return baseMapper.domainToDto(toDeleteModel);
    }
}