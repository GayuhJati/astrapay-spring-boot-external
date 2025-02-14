package com.astrapay.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotesRequestDto {
    private String title;
    private String content;
}
