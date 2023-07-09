package ru.skypro.lessons.webdevelopment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidderDto {

    @NotBlank
    @Size(min = 1, max = 15)
    private String bidderName;
}
