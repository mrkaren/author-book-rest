package com.example.authorbookrest.dto;

import com.example.authorbookrest.entity.Language;
import lombok.Data;

@Data
public class BookSearchDto {

    private String title;
    private String description;
    private String serialNumber;
    private String authorName;

    private Language language;
    private double minPrice;
    private double maxPrice;

    private String sortBy;
    private String sortDirection;


}
