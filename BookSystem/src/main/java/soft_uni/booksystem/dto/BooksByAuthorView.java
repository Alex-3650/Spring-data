package soft_uni.booksystem.dto;



import java.time.LocalDate;

public class BooksByAuthorView {
    private String title;
    private LocalDate releaseDate;
    private int copies;

    public BooksByAuthorView(String title, LocalDate releaseDate, int copies) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.copies = copies;
    }

    @Override
    public String toString() {
        return "%s %s %s".formatted(title, releaseDate, copies);
    }
}
