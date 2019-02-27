import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.text.NumberFormat;
import java.util.Locale;

public class Terrain extends JPanel implements MouseListener {
    private double[][] tabTerrain;
    private int width, height, x_nb, y_nb;
    private Image[] tabImage;

    private static final double[][] map_1 = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 1, 1, 1, 2, 2, 2, 2, 1, 1, 1, 1, 2, 2, 2, 1, 1 },
            { 0, 1, 1, 1, 2, 2, 2, 2, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1 },
            { 0, 0, 1, 1, 1, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 1, 1 },
            { 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2 },
            { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2 },
            { 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2 },
            { 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 0 },
            { 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
            { 0, 0, 1, 1, 1, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 0, 0, 0 },
            { 0, 0, 1, 1, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
            { 0, 0, 0, 1, 1, 1, 2, 2, 2, 2, 1, 1, 1, 1, 1, 0, 0, 0 },
            { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
            { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 },
            { 0, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
            { 0, 0, 0, 0, 2, 2, 2, 2, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 },
            { 0, 0, 0, 2, 2, 2, 2, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1 },
            { 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 0 },
            { 0, 0, 0, 0, 0, 2, 2, 2, 2, 1, 1, 1, 1, 0, 0, 0, 0, 0 } };

    public Terrain(int width, int height) {
        this.width = width;
        this.height = height;
        this.x_nb = width / 50 - 1;
        this.y_nb = height / 50 - 1;
        this.tabTerrain = new double[x_nb][y_nb];
        this.tabImage = new Image[6];
        initialize();
        addMouseListener(this);
        this.setLayout(null);
        this.setVisible(true);
    }

    public void initialize() {
        for (int i = 0; i < x_nb / 2; i++) {
            for (int j = 0; j < y_nb; j++) {
                tabTerrain[i][j] = map_1[j][i];
            }
        }
        for (int i = x_nb / 2; i < x_nb; i++) {
            for (int j = 0; j < y_nb; j++) {
                tabTerrain[i][j] = map_1[j][x_nb - i - 1];
            }
        }

        tabImage[0] = new ImageIcon(this.getClass().getResource("ground_ocean.png")).getImage();
        tabImage[1] = new ImageIcon(this.getClass().getResource("ground_green.png")).getImage();
        tabImage[2] = new ImageIcon(this.getClass().getResource("ground_desert.png")).getImage();
        tabImage[3] = new ImageIcon(this.getClass().getResource("selection.png")).getImage();
        /*
         * tabImage[3] = new
         * ImageIcon(this.getClass().getResource("ground_desert.png")).getImage();
         * tabImage[4] = new
         * ImageIcon(this.getClass().getResource("ground_desert.png")).getImage();
         * tabImage[5] = new
         * ImageIcon(this.getClass().getResource("ground_desert.png")).getImage();
         */
    }

    public void harmonisation() {
        for (int i = 0; i < x_nb; i++) {
            for (int j = 0; j < y_nb; j++) {
                if (i > 0 && i < x_nb - 1 && j > 0 && j < y_nb - 1) {
                    tabTerrain[i][j] = (tabTerrain[i - 1][j] + tabTerrain[i + 1][j] + tabTerrain[i][j - 1]
                            + tabTerrain[i][j + 1]) / 4;
                }
            }
        }

        for (int i = 0; i < x_nb; i++) {
            for (int j = 0; j < y_nb; j++) {
                if (i == 0 || i == x_nb - 1) {
                    if (j == 0 || j == y_nb - 1) {
                        tabTerrain[i][j] = 0;
                    }
                }
            }
        }
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);
        int decimal = 0;
        double fractionnal = 0;
        for (int i = 0; i < x_nb; i++) {
            for (int j = 0; j < y_nb; j++) {
                if (tabTerrain[i][j] < 1) {
                    g.drawImage(tabImage[0], i * 50, j * 50, this);
                } else if (tabTerrain[i][j] >= 1 && tabTerrain[i][j] < 2) {
                    g.drawImage(tabImage[1], i * 50, j * 50, this);
                } else if (tabTerrain[i][j] >= 2) {
                    g.drawImage(tabImage[2], i * 50, j * 50, this);
                }
                decimal = (int) (tabTerrain[i][j]);
                fractionnal = tabTerrain[i][j] - decimal;
                if (fractionnal != 0) {
                    g.drawImage(tabImage[3], i * 50, j * 50, this);
                    tabTerrain[i][j] = tabTerrain[i][j] - fractionnal;
                }
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (x < 36 * 50 && y < 19 * 50) {
            for(int i = 0;  i < 36; i++)
            {
                for(int j = 0; j < 19; j++)
                {
                    if(x > i*50 && x < (i+1)*50 && y < (j+1)*50 && y > j*50)
                    {
                        tabTerrain[i][j] = tabTerrain[i][j]+0.1;
                    }
                }
            }
        }
        repaint();
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }
	
}