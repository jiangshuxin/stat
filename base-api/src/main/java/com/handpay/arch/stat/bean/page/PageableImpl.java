package com.handpay.arch.stat.bean.page;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fczheng on 2016/11/29.
 */
public class PageableImpl<T> extends PageImpl<T> {
    private static final long serialVersionUID = 1L;
    private Pageable pageable;
    private long total;

    public PageableImpl(){
        super(new ArrayList<T>());
    }

    public PageableImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
        this.pageable = pageable;
        this.total = total;
    }

    public PageableImpl(List<T> content) {
        super(content);
    }

    public int getTotalPages() {
        return this.getSize() == 0?1:(int)Math.ceil((double)this.total / (double)this.getSize());
    }

    public long getTotalElements() {
        return this.total;
    }

    public boolean hasNext() {
        return this.getNumber() + 1 < this.getTotalPages();
    }

    public int getNumber() {
        return this.pageable == null?0:this.pageable.getPageNumber();
    }

    public int getSize() {
        return this.pageable == null?0:this.pageable.getPageSize();
    }

    public boolean hasPrevious() {
        return this.getNumber() > 0;
    }

    public boolean isFirst() {
        return !this.hasPrevious();
    }

    public boolean isLast() {
        return !this.hasNext();
    }
}
