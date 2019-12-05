import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.RandomAccessFile;

public class DeleteRecord extends Dialog
        implements ActionListener {
    private RandomAccessFile file;
    private JTextField recID;
    private JButton cancel, delete;
    private Record data;
    private int theRecID =  -1;
    private String pData[][];
    private HardwareStore hwStore;

    public final static String SPACE = " ";

    public DeleteRecord( HardwareStore hw_store,  RandomAccessFile f,
                      JTable tab, String p_Data[] []  )  {

        super( new Frame(), "Delete Record", true );
        setSize( 400, 150 );
        setLayout( new GridLayout( 2, 2 ) );
        file = f;
        pData = p_Data ;
        hwStore = hw_store ;
        deleteSetup() ;
    }

    public void deleteSetup() {
        JLabel recIDLabel = new JLabel( "Record ID" );
        recID  = new JTextField( 10 );
        delete = new JButton( "Delete Record" );
        cancel = new JButton( "Cancel" );

        cancel.addActionListener( this );
        delete.addActionListener( this );
        recID.addActionListener( this );

        add( recIDLabel);
        add( recID );
        add( delete );
        add( cancel );

        data = new Record();
    }

    public void actionPerformed( ActionEvent e )   {
        System.out.println( "DeleteRec(): 1a - In the actionPerformed() method. ") ;
        if ( e.getSource() == recID )  {
            theRecID = Integer.parseInt( recID.getText() );

            if ( theRecID < 0 || theRecID > 250 ) {
                recID.setText( "Invalid part number" );
            }
            else {

                try {
                    file = new RandomAccessFile( hwStore.getaFile(), "rw" );

                    file.seek(  theRecID * data.getSize() );
                    data.ReadRec( file );
                    System.out.println( "DeleteRec(): 1b - The record read is recid " +
                            data.getRecID() + SPACE +
                            data.getToolType() + SPACE +
                            data.getBrandName() + SPACE +
                            data.getToolDesc() + SPACE +
                            data.getQuantity() + SPACE +
                            data.getCost() );
                }
                catch ( IOException ex ) {
                    recID.setText( "Error reading file" );
                }
            }
        }
        else if ( e.getSource() == delete ) {
            theRecID = Integer.parseInt( recID.getText() );

            for (String[] pDatum : pData) {
                if ((pDatum[0]).equals("" + theRecID)) {
                    theRecID = Integer.parseInt(pDatum[0]);
                    System.out.println("DeleteRec(): 2 - The record id was found is  "
                            + pDatum[0]);
                    break;
                }
            }

            try {

                System.out.println( "DeleteRec(): 3 - The data file is " + hwStore.getaFile() +
                        "The record to be deleted is " +  theRecID) ;
                file = new RandomAccessFile( hwStore.getaFile(), "rw" );
                data.setRecID( theRecID ) ;

                hwStore.setEntries( hwStore.getEntries() - 1 );
                System.out.println( "DeleteRec(): 4 - Go to the beginning of the file.") ;
                file.seek(0);
                file.seek( ( theRecID ) * data.getSize() );
                data.ReadRec( file );
                System.out.println( "DeleteRec(): 5 - Go to the record " + theRecID + " to be deleted.") ;
                data.setRecID( -1 );
                System.out.println( "DeleteRec(): 6 - Write the deleted file to the file.") ;
                file.seek(0);
                file.seek( ( theRecID ) * data.getSize() );
                data.writeInteger( file , -1 );

                System.out.println( "DeleteRec(): 7 - The number of entries is " +
                        hwStore.getEntries()) ;

                file.close() ;
            }
            catch ( IOException ex ) {
                recID.setText( "Error writing file" );
                return;
            }

            int toCont = JOptionPane.showConfirmDialog(null,
                    "Do you want to delete another record? \nChoose one",
                    "Select Yes or No",
                    JOptionPane.YES_NO_OPTION);

            if ( toCont == JOptionPane.YES_OPTION  )  {
                recID.setText( "" );
            }
            else {
                closeDeleteRecordDialog();
            }
        }
        else if ( e.getSource() == cancel ) {
            closeDeleteRecordDialog( );
        }
    }

    private void closeDeleteRecordDialog()   {
        try {
            System.out.println( "DeleteRec(): 3 - The data file is " + hwStore.getaFile() +
                    "The record to be deleted is " +  theRecID) ;
            file = new RandomAccessFile( hwStore.getaFile(), "rw" );

            hwStore.Redisplay(  file, pData ) ;
            file.close() ;
        }
        catch ( IOException ex ) {
            recID.setText( "Error writing file" );
            return;
        }
        setVisible( false );
        recID.setText( "" );
    }
}