package soft_uni.booksystem.dto;

public class AuthorBookCountView {
    private String firstName;
    private String lastName;
    private long bookCount;

    public AuthorBookCountView(String firstName, String lastName, long bookCount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bookCount = bookCount;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + bookCount;
    }
}
