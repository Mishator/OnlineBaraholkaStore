package ru.skypro.homework.dto.listing;

import lombok.Data;

@Data
public class ExtendedListingDTO {
    private Long pk;
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;
    private String image;
    private String phone;
    private Integer price;
    private String title;
}


