package com.saber.site.repositories;

public class SearchResult<T> {
    private final T entity;

    public SearchResult(T entity) {
        this.entity = entity;
    }

    public T getEntity() {
        return entity;
    }


}
