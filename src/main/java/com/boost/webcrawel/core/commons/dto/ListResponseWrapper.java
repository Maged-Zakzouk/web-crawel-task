package com.boost.webcrawel.core.commons.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class ListResponseWrapper<T> implements Serializable {

    private static final long serialVersionUID = 7406407370431353543L;

    private List<T> result;

    private Metadata metadata;

    public static final ListResponseWrapper EMPTY_LIST_RESPONSE = new ListResponseWrapper(List.of(), 0, 0l);

    public ListResponseWrapper(List items, Integer totalPages, Long totalItems) {
        result = items;
        metadata = new Metadata();
        metadata.setTotalItems(totalItems);
        metadata.setTotalPages(totalPages);
    }

    @JsonIgnore
    public Long getTotalItems() {
        return metadata.getTotalItems();
    }

    @JsonIgnore
    public Integer getTotalPages() {
        return metadata.getTotalPages();
    }
}

@Data
class Metadata {
    private Integer totalPages = 0;
    private Long totalItems = 0L;

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }
}
