import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.Math.round;

public class Calculator {
    ArrayList<Token> tokens;
    static final TokenScanner scanner = new TokenScanner("");
    public Calculator(ArrayList<Token> tokens){
        this.tokens = tokens;
    }
    public static double calculate(String text){
        scanner.setText(text);
        ArrayList<Token> tokens = scanner.scan();
        return evaluate(tokens);
    }
    public static double evaluate(ArrayList<Token> tokens){
        //take care of parentheses recursively
        for(int i = 0; i < tokens.size(); i++){
            if(tokens.get(i).type == TokenType.LPAREN){
                int closingIndex = findClosingParen(i, tokens);
                if(closingIndex == i){// no closing paren was found
                    error("No closing parentheses was found");
                    return Double.NaN;
                }
                ArrayList<Token> subsection = new ArrayList<>(tokens.subList(i + 1, closingIndex));
                double result = evaluate(subsection);
                for(int j = closingIndex - 1; j >= i; j--){
                    tokens.remove(j);
                }
                tokens.add(i, new Token(result, null, TokenType.NUMBER));
            }
        }
        int i = 0;//evaluate all functions ex: sqrt, sin
        while(i < tokens.size()){
            if(tokens.get(i).type == TokenType.LITERAL){
                double value = Double.NaN;
                if(i + 1 < tokens.size()){
                    if(tokens.get(i + 1).type == TokenType.NUMBER){
                        value = tokens.get(i + 1).value;
                    }

                }

                double result = Token.performFunc(0, value, tokens.get(i));
                if(Double.isNaN(result)){

                    //error(String.format("No function or variable named %s found", tokens.get(i).literalName));
                    return result;
                }
                double[] funcDidRun = {0, 0};

                if(tokens.get(i).type == TokenType.LITERAL){
                    funcDidRun = Token.evaluateLiteral(tokens.get(i), 0.0);
                    if(funcDidRun[1] == 0 && Double.isNaN(funcDidRun[0])){//token is a variable and it is nan
                        return Double.NaN;
                    }
                }

                if(funcDidRun[1] == 1){
                    tokens.remove(i + 1); // only if a function ran should we delete the next token
                }
                tokens.remove(i);
                tokens.add(i, new Token(result, null, TokenType.NUMBER));
            }
            i++;
        }

        int operatorIndex = 0;
        while(findPriorityOperator(0, tokens) > 0){
            operatorIndex = findPriorityOperator(0, tokens);
            double leftHand = Double.NaN;
            double rightHand = Double.NaN;
            if(operatorIndex - 1 >= 0){
                if(tokens.get(operatorIndex - 1).type == TokenType.NUMBER){
                    leftHand = tokens.get(operatorIndex - 1).value;
                } else {
                    error("Invalid Syntax, argument for operation is not a number");
                    return Double.NaN;
                }
            } else {
                error("Invalid Syntax, no argument for operation");
                return Double.NaN;//
            }
            if(operatorIndex + 1 < tokens.size()){
                if(tokens.get(operatorIndex + 1).type == TokenType.NUMBER){
                    rightHand = tokens.get(operatorIndex + 1).value;
                } else {
                    error("Invalid Syntax, argument for operation is not a number");
                    return Double.NaN;
                }
            } else {
                error("Invalid Syntax, no argument for operation");
                return Double.NaN;
            }
            Token token = tokens.get(operatorIndex);
            double result = Token.performFunc(leftHand, rightHand, token);
            if(Double.isNaN(result)){
                //error(String.format("No function or variable named %s found", token.literalName));
                return result;
            }
            tokens.remove(operatorIndex + 1);
            tokens.remove(operatorIndex);
            tokens.remove(operatorIndex - 1);
            tokens.add(operatorIndex - 1, new Token(result, null, TokenType.NUMBER));
        }

//        Token.displayTokens(tokens);
        double result = tokens.get(0).value;
        DecimalFormat df = new DecimalFormat("####0.00000");
        double rounded = Double.parseDouble(df.format(result));
        return rounded;
    }
    public static void error(String msg){
        System.out.println(String.format("Error in equation: %s", msg));
    }
    private static int findPriorityOperator(int start, ArrayList<Token> tokens){
        int priorityIndex = start;
        int priorityLevel = -1;
        int currentIndex = start;
        while(currentIndex < tokens.size()){
            TokenType type = tokens.get(currentIndex).type;
            if(mapOperatorToLevel(type) > priorityLevel && type != TokenType.LPAREN && type != TokenType.RPAREN){
                priorityIndex = currentIndex;
                priorityLevel = mapOperatorToLevel(type);
            }
            if(type == TokenType.RPAREN || type == TokenType.LPAREN){
                return priorityIndex;
            }
            currentIndex++;
        }
        return priorityIndex;
    }
    private static int mapOperatorToLevel(TokenType op){
        switch (op){
            case PLUS:
            case MINUS:
                return 0;
            case MUL:
            case DIV:
            case MOD:
                return 1;
            case POW:
                return 2;
            case LPAREN:
            case RPAREN:
                return 3;
            default:
                return -1;
        }
    }

    public static int findClosingParen(int i, ArrayList<Token> tokens){ // i for starting index
        int current = i + 1;
        int currentLevel = 1;
        while(currentLevel > 0){
            if(current >= tokens.size()){
                return i;
            }
            if(tokens.get(current).type == TokenType.LPAREN){
                currentLevel++;
            } else if(tokens.get(current).type == TokenType.RPAREN) {
                currentLevel--;
            }
            current++;
        }
        return current;
    }
}
