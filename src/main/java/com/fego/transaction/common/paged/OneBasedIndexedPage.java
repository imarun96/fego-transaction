package com.fego.transaction.common.paged;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Page Impl to use in paged responses as to client we are 1 based indexed
 * paging. All controllers that return a page should return a
 * OneBasedIndexedPage instead of just Page.
 *
 * @param <T>
 */
public class OneBasedIndexedPage<T> extends PageImpl<T> {

    public OneBasedIndexedPage(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public OneBasedIndexedPage(List<T> content) {
        super(content);
    }

    // Constructor that takes in Page.
    // This way at domain level can remain 0 based indexed
    // and only at controller it will construct the custom impl
    // using the original format.
    public OneBasedIndexedPage(Page page) {
        super(page.getContent(), page.getPageable(), page.getTotalElements());
    }

    @Override
    public boolean isFirst() {
        return getNumber() == 1;
    }

    @Override
    public boolean isLast() {
        return getNumber() == super.getTotalPages();
    }

    @Override
    public int getNumber() {
        // 1 based index, so number will always incremented by 1.
        return super.getNumber() + 1;
    }

    @Override
    public Pageable getPageable() {
        Pageable basePagable = super.getPageable();
        return new OneBasedIndexedPageRequest(basePagable.getPageNumber(), basePagable.getPageSize(),
                basePagable.getSort());
    }
}