package ru.skypro.homework.dto.listing;

import lombok.*;

/**
 * <b>Класс ListingDTO представляет собой объект передачи данных (DTO),
 * который используется для хранения информации о конкретном объявлении
 * в системе. </b> <p>
 *
 * <p>Этот класс наследует от {@link ListingsDTO} и добавляет дополнительные
 * поля, связанные с автором объявления, изображением, ценой и заголовком.</p>
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListingDTO extends ListingsDTO {

    /**
     * <b>Уникальный идентификатор автора объявления. </b> <p>
     */
    private Long author;

    /**
     * <b>URL изображения товара или услуги. </b> <p>
     */
    private String image;

    /**
     * <b>Уникальный идентификатор объявления. </b> <p>
     */
    private Long pk;

    /**
     * <b>Цена товара или услуги. </b> <p>
     */
    private Integer price;

    /**
     * <b>Заголовок объявления. </b> <p>
     */
    private String title;

    public void setAuthor(Long author) {
        this.author = author;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPk(Long pk) {
        this.pk = pk;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
