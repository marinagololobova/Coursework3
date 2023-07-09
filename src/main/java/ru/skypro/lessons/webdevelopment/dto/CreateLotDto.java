package ru.skypro.lessons.webdevelopment.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLotDto {

    @NotBlank
    @Size(min = 3, max = 64)
    private String title;
    @NotBlank
    @Size(min = 1, max = 4096)
    private String description;
    @NotNull
    @Min(1)
    private Integer startPrice;
    @NotNull
    @Min(1)
    private Integer bidPrice;
}
