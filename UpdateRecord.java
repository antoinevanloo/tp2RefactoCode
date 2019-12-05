import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.RandomAccessFile;

public class UpdateRecord extends Dialog implements ActionListener {
    private RandomAccessFile file;
    private JTextField recID, toolType, brandName, toolDesc,
            partNum, quantity, price;
    private JLabel recIDLabel, toolTypeLabel, brandNameLabel,
            toolDescLabel, partNumLabel, quantityLabel,
            priceLabel;
    private JButton cancel, save;
    private Record data;
    private int theRecID, ii, iii, toCont, loopCtrl;
    private String pData[][];
    private HardwareStore hwstore;
    private boolean found = false;
    private final static int NUMBER_COLUMNS_10 = 10;

    public UpdateRecord(HardwareStore hw_store, RandomAccessFile f,
                        String p_Data[][], int iiPassed) {


        super(new Frame(), "Update Record", true);
        setSize(400, 280);
        setLayout(new GridLayout(9, 2));
        file = f;
        pData = p_Data;
        ii = iiPassed;
        hwstore = hw_store;

        upDSetup();
    }

    public void upDSetup() {

        recID = new JTextField(NUMBER_COLUMNS_10);
        toolType = new JTextField(NUMBER_COLUMNS_10);
        brandName = new JTextField(NUMBER_COLUMNS_10);
        toolDesc = new JTextField(NUMBER_COLUMNS_10);
        partNum = new JTextField(NUMBER_COLUMNS_10);
        quantity = new JTextField(NUMBER_COLUMNS_10);
        price = new JTextField(NUMBER_COLUMNS_10);

        recIDLabel = new JLabel("Record ID");
        toolTypeLabel = new JLabel("Type of Tool");
        brandNameLabel = new JLabel("Brand Name");
        toolDescLabel = new JLabel("Tool Description");
        partNumLabel = new JLabel("Part Number");
        quantityLabel = new JLabel("Quantity");
        priceLabel = new JLabel("Price");

        save = new JButton("Save Changes");
        cancel = new JButton("Cancel");

        recID.addActionListener(this);
        save.addActionListener(this);
        cancel.addActionListener(this);

        /** Add the labels and text fields to the
         *  GridLayout manager context */
        add(recIDLabel);
        add(recID);
        add(toolTypeLabel);
        add(toolType);
        add(brandNameLabel);
        add(brandName);
        add(toolDescLabel);
        add(toolDesc);
        add(partNumLabel);
        add(partNum);
        add(quantityLabel);
        add(quantity);
        add(priceLabel);
        add(price);
        add(save);
        add(cancel);

        data = new Record();
    }

    public boolean checkDigit(String strVal) {

        int strLength = 0;
        boolean notDig = true;

        strLength = strVal.length();

        for (int ii = 0; ii < strLength; ii++) {
            if (!Character.isDigit(strVal.charAt(ii))) {
                notDig = false;
                break;
            }
        }
        return notDig;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == recID) {
            if (checkDigit(recID.getText())) {
                theRecID = Integer.parseInt(recID.getText());
            } else if (theRecID < 0 || theRecID > 250) {
                JOptionPane.showMessageDialog(null,
                        "A recID entered was:  less than 0 or greater than 250, which is invalid.\n" +
                                "Please enter a number greater than 0 and less than 251.", "RecID Entered",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            theRecID = Integer.parseInt(recID.getText());

            System.out.println("UpdateRecord(): 2a - The record id being sought is " + theRecID);

            for (String[] pDatum : pData) {
                if (pDatum[0] != null) {
                    if (Integer.parseInt(pDatum[0]) == theRecID) {
                        theRecID = Integer.parseInt(pDatum[0]);
                        found = true;
                        System.out.println("UpdateRecord(): 2b - The record id was found.");
                        break;
                    }
                }
            }

            try {
                file = new RandomAccessFile(hwstore.getaFile(), "rw");
                file.seek((theRecID) * data.getSize());
                data.ReadRec(file);

                recID.setText("" + theRecID);
                toolType.setText(data.getToolType().trim());
                brandName.setText(data.getBrandName().trim());
                toolDesc.setText(data.getToolDesc().trim());
                partNum.setText(data.getPartNumber().trim());
                quantity.setText(Integer.toString(data.getQuantity()));
                price.setText(data.getCost().trim());
                System.out.println("UpdateRecord(): 2c - The record found was " +
                        data.getRecID() + " " +
                        data.getBrandName() + " " +
                        data.getToolDesc() + " " +
                        data.getQuantity() + " " +
                        data.getCost() + " in file " + hwstore.getaFile());
            } catch (IOException ex) {
                recID.setText("UpdateRecord(): 2d -  Error reading file");
            }

            if (data.getRecID() < 0) {
                recID.setText("This record " +
                        theRecID + " does not exist");
            }
        } else if (e.getSource() == save) {
            try {
                data.setRecID(Integer.parseInt(recID.getText()));
                data.setToolType(toolType.getText().trim());
                data.setBrandName(brandName.getText().trim());
                data.setToolDesc(toolDesc.getText().trim());
                data.setPartNumber(partNum.getText().trim());
                data.setQuantity(Integer.parseInt(quantity.getText().trim()));
                data.setCost(price.getText().trim());

                file.seek(0);
                file.seek(theRecID * data.getSize());
                data.write(file);

                System.out.println("UpdateRecord(): 3 - The record found was " +
                        data.getRecID() + " " +
                        data.getBrandName() + " " +
                        data.getToolDesc() + " " +
                        data.getQuantity() + " " +
                        data.getCost() + " in file " + hwstore.getaFile());

                hwstore.Redisplay(file, pData);
            } catch (IOException ex) {
                recID.setText("Error writing file");
                return;
            }

            toCont = JOptionPane.showConfirmDialog(null,
                    "Do you want to add another record? \nChoose one",
                    "Choose one",
                    JOptionPane.YES_NO_OPTION);

            if (toCont == JOptionPane.YES_OPTION) {
                recID.setText("");
                toolType.setText("");
                quantity.setText("");
                brandName.setText("");
                toolDesc.setText("");
                partNum.setText("");
                price.setText("");
            } else {
                upClear();
            }
        } else if (e.getSource() == cancel) {
            setVisible(false);
            upClear();
        }
    }

    private void upClear() {
        recID.setText("");
        brandName.setText("");
        quantity.setText("");
        price.setText("");
        setVisible(false);
    }
}