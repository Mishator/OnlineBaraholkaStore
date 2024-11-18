package ru.skypro.homework.dto.comment;

import lombok.*;

/**
 * <b>Класс представляет собой DTO (Data Transfer Object) для создания или обновления комментария. </b> <p>
 *
 * <p>Содержит текст комментария, который может быть использован как при создании нового комментария,
 * так и при обновлении существующего.</p>
 *
 * <p>Используется в слоях приложения, связанных с обработкой комментариев.</p>
 */
@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateComment {

    /**
     * <b>Текст комментария. </b> <p>
     *
     * <p>Должен содержать содержимое комментария, которое будет отображаться пользователям.</p>
     */
    private String text;

}
