package ru.skypro.lessons.webdevelopment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidDto {

    private String bidderName;
    private OffsetDateTime bidDate;
}
