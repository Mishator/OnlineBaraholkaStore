package ru.skypro.homework.dto.listing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <b>Класс представляет собой DTO (Data Transfer Object) для создания или обновления объявления. </b> <p>
 *
 * <p>Содержит информацию о заголовке, цене и описании объявления, которые могут быть использованы
 * как при создании нового объявления, так и при обновлении существующего.</p>
 *
 * <p>Используется в слоях приложения, связанных с обработкой объявлений.</p>
 */
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateListing {

    /**
     * <b>Заголовок объявления. </b> <p>
     *
     * <p>Должен содержать краткое и информативное название объявления.</p>
     */
    private String title;

    /**
     * <b>Цена объявления. </b> <p>
     *
     * <p>Должна быть указана в целых числах и представлять стоимость товара или услуги.</p>
     */
    private int price;

    /**
     * <b>Описание объявления. </b> <p>
     *
     * <p>Должно содержать детальную информацию о товаре или услуге, предлагаемых в объявлении.</p>
     */
    private String description;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
