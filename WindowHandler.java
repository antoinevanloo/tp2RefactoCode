import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowHandler extends WindowAdapter {
    HardwareStore hardwareStore;

    public WindowHandler(HardwareStore hardwareStore) {
        this.hardwareStore = hardwareStore;
    }

    public void windowClosing(WindowEvent windowEvent) {
        hardwareStore.cleanup();
    }
}