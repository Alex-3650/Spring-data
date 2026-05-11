import java.util.Scanner;

public class demo {
    public static void main(String[] args) {

         Scanner scanner = new Scanner(System.in);

         String userName=scanner.nextLine();

         String query="SELECT id FROM users WHERE userName=%s";
        System.out.printf((query) + "%n",userName);
    }
}
