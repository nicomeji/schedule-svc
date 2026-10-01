package com.gym.platform.schedule.rest.contract.common;

import java.util.List;

import lombok.Data;

@Data
public class PageDTO<T> {
    private List<T> searchedElements;
    private SearchedPageDTO searchedPage;
    private Long totalElements;
}
