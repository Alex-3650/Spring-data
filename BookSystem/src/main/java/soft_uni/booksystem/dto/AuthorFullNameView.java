package soft_uni.booksystem.dto;

public class AuthorFullNameView {
    private String firstName;
    private String lastName;

    public AuthorFullNameView(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
