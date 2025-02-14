package com.astrapay.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse<T> {
    private String status;
    private List<String> message = new ArrayList<>();
    private String messages;
    private T data;

    public APIResponse(T data, HttpStatus httpStatus) {
        this.status = httpStatus.name();
        this.data = data;
    }

    public APIResponse(String messages) {
        this.status = "Berhasil";
        this.message.add(messages);
    }

}
