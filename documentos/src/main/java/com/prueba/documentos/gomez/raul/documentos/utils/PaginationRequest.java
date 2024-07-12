package com.prueba.documentos.gomez.raul.documentos.utils;

import io.micrometer.common.lang.NonNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaginationRequest<T> {

    @NonNull
    private int page;

    @NonNull
    private int size;

    private T data;

    public PaginationRequest(int page, int size) {
        if (page < 1) {
            throw new IllegalArgumentException("Page number must be greater than 0");
        }
        if (size < 1) {
            throw new IllegalArgumentException("Page size must be greater than 0");
        }
        this.page = page;
        this.size = size;
    }

    public int getOffset(){
        return (page - 1) * size;
    }

    public String getQueryBySql(String sql){
        return sql + " LIMIT ? OFFSET ?";
    }
}
