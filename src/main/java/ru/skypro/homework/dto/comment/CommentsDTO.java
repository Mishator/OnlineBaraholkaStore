package ru.skypro.homework.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <b>DTO (Data Transfer Object) класс для представления комментариев. </b> <p>
 * <p>
 * Этот класс используется для передачи информации о комментариях, включая общее количество комментариев
 * и список самих комментариев.
 * </p>
 */
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentsDTO {

    /**
     * <b>Общее количество комментариев. </b> <p>
     */
    private int count;

    /**
     * <b>Список комментариев, представленных в виде объектов {@link CommentDTO}. </b> <p>
     */
    private List<CommentDTO> results;

    public void setCount(int count) {
        this.count = count;
    }

    public void setResults(List<CommentDTO> results) {
        this.results = results;
    }
}
