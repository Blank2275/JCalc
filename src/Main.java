import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        TokenScanner scanner = new TokenScanner(" 2 + PI^2");//
        ArrayList<Token> tokens = scanner.scan();

        System.out.println(Calculator.evaluate(tokens));
    }
}
