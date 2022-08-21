import java.util.ArrayList;
import java.util.HashMap;

public class Token {
    double value;
    TokenType type;
    String literalName; //ex: sqrt, sin, cos
    static HashMap<String, Double> variables = new HashMap<String, Double>();
    public Token(double value, String literalName, TokenType type){
        this.value = value;
        this.type = type;
        this.literalName = literalName;
        Token.variables.put("PI", 3.14159);
    }

    static double performFunc(double a, double b, Token t){
        switch (t.type){
            case PLUS:
                return a + b;
            case MINUS:
                return  a - b;
            case MUL:
                return a * b;
            case DIV:
                return a / b;
            case MOD:
                return a % b;
            case POW:
                return Math.pow(a, b);
            case LITERAL:
                return evaluateLiteral(t,b)[0];
            default:
                return Double.NaN;
        }
    }

    public static double[] evaluateLiteral(Token t,  double b){
        switch(t.literalName){
            case "sqrt":
                return new double[]{Math.sqrt(b), 1.0}; //1 means a function was called
            default:
                if(Token.variables.containsKey(t.literalName)){
                    return new double[]{(double)Token.variables.get(t.literalName), 0.0};
                }
                return new double[]{Double.NaN, 0.0};
        }
    }


    public static void display(Token t){
        String msg = generateDisplayString(t);
        System.out.println(msg);//
    }
    private static String generateDisplayString(Token t){
        String msg = "";
        switch (t.type){
            case LPAREN:
                msg = "Symbol: (";
                break;
            case RPAREN:
                msg = "Symbol: )";
                break;
            case PLUS:
                msg = "Symbol: +";
                break;
            case MINUS:
                msg = "Symbol: -";
                break;
            case MUL:
                msg = "Symbol: *";
                break;
            case DIV:
                msg = "Symbol: /";
                break;
            case MOD:
                msg = "Symbol: %";
                break;
            case POW:
                msg = "Symbol: ^";
                break;
            case LITERAL:
                msg = String.format("Literal: %s", t.literalName);
                break;
            case NUMBER:
                msg = String.format("Number: %s", t.value);
                break;
        }
        return msg;
    }
    public static void displayTokens(ArrayList<Token> tokens){
        for(Token token: tokens){
            System.out.print(generateDisplayString(token) + "  ");
        }
        System.out.println("");
    }
}
