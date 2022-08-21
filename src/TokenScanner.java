import java.util.ArrayList;
public class TokenScanner {
    String text;
    int start;
    int current;
    ArrayList<Token> tokens;

    public TokenScanner(String text){
        this.text = text;
        this.start = 0;
        this.current = 0;
        this.tokens = new ArrayList<Token>();
    }
    public void setText(String text){
        this.text = text;
    }
    public ArrayList<Token> scan(){
        tokens = new ArrayList<Token>();
        start = 0;
        current = 0;
        while(!isAtEnd()){
            start = current;

            scanToken();
        }
        return tokens;
    }
    void scanToken(){//
        char c = advance();
        switch (c){
            case ' ':
                break;
            case '\t':
                break;
            case '+':
                tokens.add(new Token(0, null, TokenType.PLUS));
                break;
            case '-':
                tokens.add(new Token(0, null, TokenType.MINUS));
                break;
            case '*':
                tokens.add(new Token(0, null, TokenType.MUL));
                break;
            case '/':
                tokens.add(new Token(0, null, TokenType.DIV));
                break;
            case '^':
                tokens.add(new Token(0, null, TokenType.POW));
                break;
            case '%':
                tokens.add(new Token(0, null, TokenType.MOD));
                break;
            case '(':
                tokens.add(new Token(0, null, TokenType.LPAREN));
                break;
            case ')':
                tokens.add(new Token(0, null, TokenType.RPAREN));
                break;
            default:
                if(isDigit(c)){
                    number();
                } else if(isAlpha(c)){
                    literal();
                }
                break;
        }
    }
    boolean isDigit(char c){
        return (c >= '0' && c <= '9');
    }
    boolean isAlpha(char c){
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
    }
    void literal(){
        while(isAlpha(peek()) && !isAtEnd()) advance();

        String value = text.substring(start, current);

        tokens.add(new Token(0, value, TokenType.LITERAL));

    }

    void number(){
        while (isDigit(peek())) advance();
        if(peek() == '.' && isDigit(peekNext())){
            advance();

            while (isDigit(peek())) advance();
        }
        tokens.add(new Token(Double.parseDouble(text.substring(start, current)), null, TokenType.NUMBER));
    }
    char advance(){
        return text.charAt(current++);
    }
    char peek(){
        if (isAtEnd()) return '\0';
        return text.charAt(current);
    }
    char peekNext(){
        if (isAtEnd()) return '\0';
        return text.charAt(current + 1);
    }
    boolean isAtEnd(){
        return current >= text.length();
    }
}
