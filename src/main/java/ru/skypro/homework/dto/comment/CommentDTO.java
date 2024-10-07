package ru.skypro.homework.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <b>Класс CommentDTO представляет собой объект передачи данных (DTO),
 * который используется для передачи информации о комментарии. </b> <p>
 *
 * <p>Содержит информацию об авторе комментария, времени создания,
 * идентификаторе комментария и тексте самого комментария.</p>
 *
 * <p>Использует аннотации Lombok для автоматической генерации
 * геттеров, конструкторов и других методов.</p>
 */
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    /**
     * <b>Идентификатор автора комментария. </b> <p>
     */
    private long author;

    /**
     * <b>URL изображения автора комментария. </b> <p>
     */
    private String authorImage;

    /**
     * <b>Имя автора комментария. </b> <p>
     */
    private String authorFirstName;

    /**
     * <b>Дата и время создания комментария. </b> <p>
     */
    private LocalDateTime createdAt;

    /**
     * <b>Уникальный идентификатор комментария. </b> <p>
     */
    private long pk;

    /**
     * <b>Текст комментария. </b> <p>
     */
    private String text;

    public void setAuthor(long author) {
        this.author = author;
    }

    public void setAuthorImage(String authorImage) {
        this.authorImage = authorImage;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    public void setText(String text) {
        this.text = text;
    }
}
