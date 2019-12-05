import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowHandler extends WindowAdapter {
    HardwareStore h;

    public WindowHandler(HardwareStore s) {
        h = s;
    }

    public void windowClosing(WindowEvent e) {
        h.cleanup();
    }
}