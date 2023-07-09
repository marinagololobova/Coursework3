package ru.skypro.lessons.webdevelopment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CsvLotDto {

    private int id;
    private String title;
    private Status status;
    private String lastBidder;
    private int currentPrice;
}
