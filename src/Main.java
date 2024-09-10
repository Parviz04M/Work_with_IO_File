import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        FileUtility.writeIntoFile("File1.txt", "Salam, my name is Frank");
        byte[] bytes = FileUtility.readBytes("File1.txt");
        System.out.println(new String(bytes));
    }
}
