package ru.skypro.homework.dto;

import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login {

    private String username;
    private String password;

}
