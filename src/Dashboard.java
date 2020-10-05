import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

class Dashboard extends JFrame {

    private final static Border OUTER = BorderFactory.createEmptyBorder(10, 10, 10, 10);
    private final static Border INNER = BorderFactory.createLineBorder(Color.BLACK, 1, true);
    final static Border BORDER = BorderFactory.createCompoundBorder(OUTER, INNER);

    ParameterPanel parameters = new ParameterPanel();
    JPanel content = new JPanel();


    Dashboard() {
        super("Smart Home Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1024, 512));
        setResizable(false);
        setLayout(new BorderLayout());
        add(parameters, BorderLayout.WEST);
        add(content, BorderLayout.EAST);
        content.setPreferredSize(new Dimension(768, 512));
        parameters.setBorder(new TitledBorder(BORDER, "Simulation"));
        content.setBorder(BORDER);

    }

    public static void main(String[] args) {
        Dashboard dashboard = new Dashboard();
        dashboard.pack();
        dashboard.setVisible(true);
        dashboard.setLocationRelativeTo(null);
    }

}
