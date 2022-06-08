package com.boost.webcrawel.utils;


import com.boost.webcrawel.core.commons.exception.LogicalException;
import com.boost.webcrawel.core.commons.exception.ServerError;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class JpaPagingUtil {
    static int PAGE_SIZE = 10;

    public static Pageable getPage(Integer pageNum, Sort.Direction sortDirection, String field) throws LogicalException {
        return getPage(pageNum, PAGE_SIZE, sortDirection, field);
    }

    public static Pageable getPage(Integer pageNum, Integer pageSize, Sort.Direction sortDirection, String... fields) throws LogicalException {
        if (validatePageNumberInput(pageNum)) return null;
        int finalPageSize = pageSize == null ? PAGE_SIZE : pageSize;
        Pageable page;
        // convert it to ZERO based paging.
        pageNum--;
        if (sortDirection == null || fields == null) {
            page = PageRequest.of(pageNum, finalPageSize);
        } else {
            page = PageRequest.of(pageNum, finalPageSize, sortDirection, fields);
        }
        return page;
    }

    public static boolean validatePageNumberInput(Integer pageNum) {
        if (pageNum == null) {
            return true;
        }
        if (pageNum <= 0) {
            throw new LogicalException(ServerError.INVALID_PAGE);
        }
        return false;
    }

    public static Pageable getPage(Integer pageNum, Integer pageSize) throws LogicalException {
        return getPage(pageNum, pageSize, null, null);
    }
}
