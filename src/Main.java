/*
The Calculator and Token Scanner classes can be reused in other projects as well
When it asks for an equation you can put things like these:
1.) 12
2.) 5 +7
3.) 5 + (4 + 3)
4.) 8 + (4 * cos(PI))

ALl valid operators:
+, -, *, /, %, ^
All valid functions:
sqrt, sin, cos, tan, asin, acos, atan, factorial, floor
All valid variables:
PI

This will be expanded on in the future
*/
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner inputScanner = new Scanner(System.in);

        boolean running = true;

        while(running){
            System.out.print("Enter an equation: ");
            String input = inputScanner.nextLine();
            if(input.equals("quit")){
                running = false;
                continue;
            }

            System.out.println(Calculator.calculate(input));
        }
    }
}
