package com.example.authorbookrest.dto;

import com.example.authorbookrest.entity.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequestDto {

    private String title;
    private String description;
    private Language language;
    private String serialNumber;
    private int authorId;

}
