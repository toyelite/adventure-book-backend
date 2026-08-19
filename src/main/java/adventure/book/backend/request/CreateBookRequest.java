package adventure.book.backend.request;

import adventure.book.backend.dto.Section;
import adventure.book.backend.enums.Difficulty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateBookRequest(
        @NotBlank String title,
        @NotBlank String author,
        @NotNull Difficulty difficulty,
        List<String> categories,
        @NotEmpty List<Section> sections
) {
}