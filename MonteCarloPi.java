import javax.swing.*;
import java.awt.*;
import java.util.Random;
public class MonteCarloPi extends JPanel {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private static final int NUM_POINTS = 1000000;
    private int pointsInsideCircle = 0;
    private int pointsInsideSquare = 0;
    public MonteCarloPi() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        generatePoints();
    }
    private void generatePoints() {
        Random random = new Random();
        for (int i = 0; i < NUM_POINTS; i++) {
            double x = random.nextDouble() * WIDTH;
            double y = random.nextDouble() * HEIGHT;
            double distance = Math.pow(x - WIDTH / 2, 2) + Math.pow(y - HEIGHT / 2, 2);
            if (distance <= Math.pow(WIDTH / 2, 2)) {
                pointsInsideCircle++;
            }
            pointsInsideSquare++;
            repaint();
        }
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int circleRadius = WIDTH / 2;
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.WHITE);
        g.fillOval(WIDTH / 2 - circleRadius, HEIGHT / 2 - circleRadius, 2 * circleRadius, 2 * circleRadius);
        g.setColor(Color.RED);
        for (int i = 0; i < pointsInsideSquare; i++) {
            double x = Math.random() * WIDTH;
            double y = Math.random() * HEIGHT;
            double distance = Math.pow(x - WIDTH / 2, 2) + Math.pow(y - HEIGHT / 2, 2);
            if (distance <= Math.pow(circleRadius, 2)) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.RED);
            }
            g.drawLine((int) x, (int) y, (int) x, (int) y);
        }
        double piEstimate = 4.0 * pointsInsideCircle / pointsInsideSquare;
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g.getFontMetrics();
        String text = "Vlera perafruese e π: " + piEstimate;
        int textWidth = fm.stringWidth(text);
        g.drawString(text, (WIDTH - textWidth) / 2, HEIGHT / 2);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Simulimi i Monte Carlo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            MonteCarloPi panel = new MonteCarloPi();
            frame.add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
