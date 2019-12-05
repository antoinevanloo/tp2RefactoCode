import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MenuHandler implements ActionListener {

    HardwareStore hardwareStore ;

    private JMenuItem fileMenuItems;
    /** View Menu Items */
    private JMenuItem lmMI, lmtMI, hdMI, dpMI, hamMI, csMI, tabMI, bandMI,
            sandMI, stapMI, wdvMI, sccMI;
    private JMenuItem deleteMenuItem, addMenuItem, updateMenuItem, listAllMenuItem ;
    private JMenuItem debugON, debugOFF ;
    /** Help Menu Items */
    private JMenuItem helpHWMenuItem;
    private JMenuItem aboutHWMenuItem;

    private RandomAccessFile file;
    private String pData[] []  = new String [ 250 ] [ 7 ];




    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == fileMenuItems) {
            /**The Exit menu Item was selected. */
            cleanup();
        } else if (e.getSource() == lmMI) {
            sysPrint("The Lawn Mower menu Item was selected.\n");

            display("Lawn Mowers");
        } else if (e.getSource() == lmtMI) {
            sysPrint("The Lawn Mower Tractor menu Item was selected.\n");

            display("Lawn Tractor Mowers");
        } else if (e.getSource() == hdMI) {
            sysPrint("The Hand Drill Tools menu Item was selected.\n");

            display("Hand Drill Tools");
        } else if (e.getSource() == dpMI) {
            sysPrint("The Drill Press Power Tools menu Item was selected.\n");

            display("Drill Press Power Tools");
        } else if (e.getSource() == csMI) {
            sysPrint("The Circular Saws Tools menu Item was selected.\n");

            display("Circular Saws");
        } else if (e.getSource() == hamMI) {
            sysPrint("The Hammer menu Item was selected.\n");

            display("Hammers");
        } else if (e.getSource() == tabMI) {
            sysPrint("The Table Saws menu Item was selected.\n");

            display("Table Saws");
        } else if (e.getSource() == bandMI) {
            sysPrint("The Band Saws menu Item was selected.\n");

            display("Band Saws");
        } else if (e.getSource() == sandMI) {
            sysPrint("The Sanders menu Item was selected.\n");

            display("Sanders");
        } else if (e.getSource() == stapMI) {
            sysPrint("The Staplers menu Item was selected.\n");

            display( "Staplers" ) ;
        }
        else if ( e.getSource() == wdvMI ) {
            sysPrint ("The Wet-Dry Vacs menu Item was selected.\n" );
        }
        else if ( e.getSource() == sccMI ) {
            sysPrint ("The Storage, Chests & Cabinets menu Item was selected.\n" );
        }
        else if ( e.getSource() == deleteMenuItem ) {
            sysPrint ("The Delete Record Dialog was made visible.\n") ;
            deleteRecordDialogBox = new DeleteRecord( hws, file, table, pData );
            deleteRecordDialogBox.setVisible( true );
        }
        else if ( e.getSource() == addMenuItem ) {
            sysPrint ("The Add menu Item was selected.\n" );
            passwordCheckingDialogBox.displayDialog( "add" ) ;
        }
        else if ( e.getSource() == updateMenuItem ) {
            sysPrint ("The Update menu Item was selected.\n" );
            updateRecordDialogBox = new UpdateRecord( hws, file,  pData, -1 );
            updateRecordDialogBox.setVisible( true );
        }
        else if ( e.getSource() == listAllMenuItem ) {
            sysPrint ("The List All menu Item was selected.\n" );
        }
        else if ( e.getSource() == debugON ) {
            myDebug = true ;
            sysPrint ("Debugging for this execution is turned on.\n" );
        }
        else if ( e.getSource() == debugOFF ) {
            sysPrint ("Debugging for this execution is turned off.\n" );
            myDebug = false ;
        }
        else if ( e.getSource() == helpHWMenuItem ) {
            sysPrint ("The Help menu Item was selected.\n" );
            File hd = new File("HW_Tutorial.html");

            Runtime rt = Runtime.getRuntime();
            String[] callAndArgs = {"c:\\Program Files\\Internet Explorer\\IEXPLORE.exe",
                    "" + hd.getAbsolutePath()};

            try {

                Process child = rt.exec(callAndArgs);
                child.waitFor();
                sysPrint("Process exit code is: " +
                        child.exitValue());
            } catch (IOException e2) {
                sysPrint(
                        "IOException starting process!");
            } catch (InterruptedException e3) {
                System.err.println(
                        "Interrupted waiting for process!");
            }
        } else if (e.getSource() == aboutHWMenuItem) {
            sysPrint("The About menu Item was selected.\n");
            Runtime rt = Runtime.getRuntime();
            String[] callAndArgs = {"c:\\Program Files\\Internet Explorer\\IEXPLORE.exe",
                    "http://www.sumtotalz.com/TotalAppsWorks/ProgrammingResource.html"};
            try {
                Process child = rt.exec(callAndArgs);
                child.waitFor();
                sysPrint("Process exit code is: " +
                        child.exitValue());
            } catch (IOException e2) {
                System.err.println(
                        "IOException starting process!");
            } catch (InterruptedException e3) {
                System.err.println(
                        "Interrupted waiting for process!");
            }
        }
        String current = (String) e.getActionCommand();
    }

    /** ********************************************************
     *  Method: sysPrint() is a debugging aid that is used to print
     *          information to the screen.
     ********************************************************/
    public void sysPrint(String str) {
        if (myDebug) {
            System.out.println(str);
        }
    }

    /** *************************************************************
     * Method: display() is used to display the contents of the
     *         specified table in the passed parameter. This method
     *         uses the passed parameter to determine
     *         1- Which table to display
     *         2- Whether the table exists
     *         3- If it exists, the table is opened and its
     *            contents are displayed in a JTable.
     *
     * Called from the actionPerformed() method of the MenuHandler class
     *********************************************************************/
    public void display(String str) {

        String df = null, title = null;

        if (str.equals("Lawn Mowers")) {
            df = new String("lawnmower.dat");
            hardwareStore.setaFile(new File("lawnmower.dat"));
            title = new String("Hardware Store: Lawn Mowers");
        } else if (str.equals("Lawn Tractor Mowers")) {
            df = new String("lawnTractor.dat");
            hardwareStore.setaFile(new File("lawnTractor.dat"));
            title = new String("Hardware Store: Lawn Tractor Mowers");
        } else if (str.equals("Hand Drill Tools")) {
            df = new String("handDrill.dat");
            hardwareStore.setaFile(new File("handDrill.dat"));
            title = new String("Hardware Store:  Hand Drill Tools");
        } else if (str.equals("Drill Press Power Tools")) {
            df = new String("drillPress.dat");
            hardwareStore.setaFile(new File("drillPress.dat"));
            title = new String("Hardware Store: Drill Press Power Tools");
        } else if (str.equals("Circular Saws")) {
            df = new String("circularSaw.dat");
            hardwareStore.setaFile(new File("circularSaw.dat"));
            title = new String("Hardware Store: Circular Saws");
        } else if (str.equals("Hammers")) {
            df = new String("hammer.dat");
            hardwareStore.setaFile(new File("hammer.dat"));
            title = new String("Hardware Store: Hammers");
        } else if (str.equals("Table Saws")) {
            df = new String("tableSaw.dat");
            hardwareStore.setaFile(new File("tableSaw.dat"));
            title = new String("Hardware Store: Table Saws");
        } else if (str.equals("Band Saws")) {
            df = new String("bandSaw.dat");
            hardwareStore.setaFile(new File("bandSaw.dat"));
            title = new String("Hardware Store: Band Saws");
        } else if (str.equals("Sanders")) {
            df = new String("sanders.dat");
            hardwareStore.setaFile(new File("sanders.dat"));
            title = new String("Hardware Store: Sanders");
        } else if (str.equals("Staplers")) {
            df = new String("stapler.dat");
            hardwareStore.setaFile(new File("stapler.dat"));
            title = new String("Hardware Store: Staplers");
        }

        try {
            /** Open the .dat file in RW mode.
             *  If the file does not exist, create it
             *  and initialize it to 250 empty records.
             */

            sysPrint("display(): 1a - checking to see if " + df + " exists.");
            if (!hardwareStore.getaFile().exists()) {

                sysPrint("display(): 1b - " + df + " does not exist.");

            } else {
                file = new RandomAccessFile(df, "rw");

                this.setTitle(title);

                hardwareStore.Redisplay(file, pData);
            }

            file.close();
        } catch (IOException e) {
            System.err.println(e.toString());
            System.err.println("Failed in opening " + df);
            System.exit(1);
        }

    }
}