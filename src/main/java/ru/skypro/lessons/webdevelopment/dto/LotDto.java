package ru.skypro.lessons.webdevelopment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class LotDto {

    private int id;
    private Status status;
    private String title;
    private String description;
    private int startPrice;
    private int bidPrice;
}
