package com.fego.transaction.common.paged;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * Class to be used by OneBasedIndexedPage for its Pageable.
 */
public class OneBasedIndexedPageRequest extends PageRequest {

    protected OneBasedIndexedPageRequest(int page, int size, Sort sort) {
        super(page, size, sort);
    }

    @Override
    public int getPageNumber() {
        return super.getPageNumber() + 1;
    }

    @Override
    public long getOffset() {
        return (getPageNumber() - 1) * (long) getPageSize();
    }
}