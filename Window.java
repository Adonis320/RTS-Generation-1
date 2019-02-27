import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;

public class Window extends JFrame {
    private int x, y, width, height;
    private Terrain my_Terrain;

    public Window(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.my_Terrain = new Terrain(this.width,this.height);
        this.setContentPane(my_Terrain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(x, y, width, height);
        this.setLayout(null);
        this.setVisible(true);

    }

    public static void main(String[] args) {
        Window a = new Window(0,0,1850,1000);
    }
}