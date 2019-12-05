import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.RandomAccessFile;

public class MouseClickedHandler extends MouseAdapter {
    private HardwareStore hwstore;

    JTable table;
    String pData[][], columnNames[];
    RandomAccessFile f;

    MouseClickedHandler(RandomAccessFile fPassed, JTable tablePassed,
                        String p_Data[][],HardwareStore hwstore) {
        table = tablePassed;
        pData = p_Data;
        f = fPassed;
        this.hwstore = hwstore;
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == table) {
            int ii = table.getSelectedRow();
            JOptionPane.showMessageDialog(null,
                    "Enter the record ID to be updated and press enter.",
                    "Update Record", JOptionPane.INFORMATION_MESSAGE);
            UpdateRecord update = new UpdateRecord(hwstore, f, pData, ii);
            if (ii < 250) {
                update.setVisible(true);
                table.repaint();
            }
        }
    }
}