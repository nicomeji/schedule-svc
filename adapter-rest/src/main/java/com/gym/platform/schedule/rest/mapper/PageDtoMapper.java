package com.gym.platform.schedule.rest.mapper;

import org.springframework.stereotype.Component;

import com.gym.platform.schedule.domain.common.Page;
import com.gym.platform.schedule.rest.contract.common.PageDTO;
import com.gym.platform.schedule.rest.contract.common.SearchedPageDTO;

@Component
public class PageDtoMapper {
    public <T> PageDTO<T> toDto(Page<T> page) {
        SearchedPageDTO searchedPageDTO = new SearchedPageDTO();
        searchedPageDTO.setSize(page.searchedPage().size());
        searchedPageDTO.setOffset(page.searchedPage().offset());

        PageDTO<T> dto = new PageDTO<>();
        dto.setSearchedElements(page.searchedElements());
        dto.setSearchedPage(searchedPageDTO);
        dto.setTotalElements(page.totalElements());

        return dto;
    }
}
