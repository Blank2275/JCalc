import javax.swing.*;
import java.awt.*;

public class App extends JFrame{
    JFrame f;
    public App(){
        JTextField input = new JTextField();

        GridLayout buttonArea = new GridLayout();

        setSize(365, 510);
        setLayout(new GridLayout());
        setVisible(true);
    }
    public static void main(String[] args){
        new App();
    }
}
