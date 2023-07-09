package ru.skypro.lessons.webdevelopment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullLotDto {

    private Integer id;
    private Status status;
    private String title;
    private String description;
    private Integer startPrice;
    private Integer bidPrice;
    private Integer currentPrice;
    private BidDto lastBid;
}
