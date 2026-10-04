package com.gym.platform.schedule.domain.common;

import java.util.List;
import java.util.function.Function;

public record Page<T>(
                List<T> searchedElements,
                SearchedPage searchedPage,
                Long totalElements) {

        public <U> Page<U> map(Function<T, U> mapper) {
                return new Page<>(
                                searchedElements.stream().map(mapper).toList(),
                                this.searchedPage,
                                this.totalElements);
        }
}
