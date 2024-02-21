package ru.skypro.homework.dto.listing;

import lombok.Data;

import java.util.List;

@Data
public class ListingsDTO {
    private int count;
    private List<ListingDTO> results;

}
