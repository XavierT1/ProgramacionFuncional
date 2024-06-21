import view.ControlPanel;
import javax.swing.JFrame;

/**
 * Main class to launch the drawing figures application.
 * Author: Freddy Tapia
 */

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Drawing Figures (Lambda)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        ControlPanel  panel = new ControlPanel();
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
