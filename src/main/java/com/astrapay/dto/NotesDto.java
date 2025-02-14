package com.astrapay.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotesDto {
    private Long id;
    private String title;
    private String content;
}
