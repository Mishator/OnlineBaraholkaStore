package ru.skypro.homework.dto.listing;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class ListingDTO extends ListingsDTO {
    private Long author;
    private String image;
    private Long pk;
    private Integer price;
    private String title;

}
