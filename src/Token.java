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

    static double performFunc(ArrayList<Double> args, Token t){
        switch (t.type){
            case PLUS:
                return args.get(0) + args.get(1);
            case MINUS:
                return  args.get(0) - args.get(1);
            case MUL:
                return args.get(0) * args.get(1);
            case DIV:
                return args.get(0) / args.get(1);
            case MOD:
                return args.get(0) % args.get(1);
            case POW:
                return Math.pow(args.get(0), args.get(1));
            case LITERAL:
                return evaluateLiteral(t, args)[0];
            default:
                return Double.NaN;
        }
    }

    public static double[] evaluateLiteral(Token t,  ArrayList<Double> args){
        switch(t.literalName){
            case "sqrt":
                return new double[]{Math.sqrt(args.get(0)), 1.0}; //1 means a function was called
            case "sin":
                return new double[]{Math.sin(args.get(0)), 1.0};
            case "cos":
                return new double[]{Math.cos(args.get(0)), 1.0};
            case "tan":
                return new double[]{Math.tan(args.get(0)), 1.0};
            case "asin":
                return new double[]{Math.asin(args.get(0)), 1.0};
            case "acos":
                return new double[]{Math.acos(args.get(0)), 1.0};
            case "atan":
                return new double[]{Math.atan(args.get(0)), 1.0};
            case "factorial":
                return new double[]{factorial(args.get(0)), 1.0};
            case "floor":
                return new double[]{Math.floor(args.get(0)), 1.0};
            case "dist":
                return new double[]{dist(args.get(0), args.get(1), args.get(2), args.get(3)), 1.0};
            default:
                if(Token.variables.containsKey(t.literalName)){
                    return new double[]{(double)Token.variables.get(t.literalName), 0.0};
                }
                return new double[]{Double.NaN, 0.0};
        }

    }

    private static double dist(double x1,double y1,double x2,double y2){
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private static double factorial(double x){
        int rounded = (int)x;
        if(rounded < 1) return Double.NaN;
        else if(rounded == 1) return 1;
        else return rounded * factorial((float)(rounded - 1));
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
            case COMMA:
                msg = "Symbol: ,";
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