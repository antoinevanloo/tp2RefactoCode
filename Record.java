/**
 * **************************************************
 * File:  Record.java
 * <p>
 * <p>
 * Copyright (c) 2002-2003 Advanced Applications Total Applications Works.
 * (AATAW)  All Rights Reserved.
 * <p>
 * AATAW grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to AATAW.
 * <p>
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. AATAW AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL AATAW OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * <p>
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 * **************************************************
 */

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;

/** *******************************************************************
 * class: The Record class' purpose is to read and write records to
 *        a randomaccess file.
 * *******************************************************************/
public class Record {
    private int recID;
    private int quantity;
    private String toolType = "";
    private String brandName = "";
    private String toolDesc = "";
    private String partNum = "";
    private String cost = "";
    private String recordTokens[];
    private boolean myDebug = false;
    private long filePos;
    private long fileLen;

    /** *******************************************************************
     *  Method: ReadRec() Reads a record from the specified RandomAccessFile.
     * 1- Read the first integer
     * 2- Read the second integer
     * 3- Read characters one at a time until we reach a string of
     *    ';;;'. This indicates that we have reached the end of the
     *    character string for this particular record.
     * 4- Load the resulting string into a StringTokenizer object.
     * 5- We are looking for 7 tokens, so if the token count is
     *    greater than 4, we will tokenize the string.
     * 6- The tokens are loaded into a string array and then into the
     *    class variables.
     ********************************************************** */

    public void ReadRec(RandomAccessFile file) throws IOException {
        char f[] = new char[585], ch;
        StringTokenizer tokens;
        String str = "", str2 = "";
        StringBuffer buf1 = new StringBuffer("");
        int ii = 0, loopCtl = 585, len = 0;
        long remm = fileLen - filePos;


        sysPrint("ReadRec() 1a: Remaining bytes is " + remm);

        sysPrint("ReadRec() 1b: Reading ints");

        recID = file.readInt();
        sysPrint("ReadRec() 1c: recID  is " + recID);

        quantity = file.readInt();

        sysPrint("ReadRec() 2: Reading string");

        /** Read characters until we get to ;;; which
         *  indicates the end of the record */
        while (ii < loopCtl) {
            ch = file.readChar();
            str = str + ch;
            len = str.length();

            if (ii > 4) {
                str2 = str.substring(len - 4, len - 1);
                if (";;;".equals(str2))
                    break;
            }

            ii++;
        }

        sysPrint("ReadRec() 3a: str is " + str);

        sysPrint("ReadRec() 3b: Reading string. ii =s " + ii);

        sysPrint("ReadRec() 4: The value of str is " + str);

        tokens = new StringTokenizer(str, ";;");


        recordTokens = new String[7];


        if (tokens.countTokens() >= 4) {
            sysPrint("ReadRec() 5: The number of tokens is " +
                    tokens.countTokens());
            ii = 2;

            /** Load the tokens into a string array. */
            while (ii < 7) {
                recordTokens[ii] = tokens.nextToken().toString();
                ii++;
            }


            toolType = new String(recordTokens[2]);
            brandName = new String(recordTokens[3]);
            partNum = new String(recordTokens[4]);
            cost = new String(recordTokens[5]);
            toolDesc = new String(recordTokens[6]);
        } else {
            sysPrint("ReadRec() 6: There are no records to read.");
        }
    }

    /** ********************************************************
     *  The fill() method is used to fill in the passed string with
     *  blanks.
     ******************************************************** */
    public StringBuffer fill(String str, StringBuffer buf, int len) {
        String strTwo = new String("                     " +
                "                                             ");

        if (str != null) {
            buf.setLength(len);
            buf = new StringBuffer(str + strTwo);
        } else
            buf = new StringBuffer(strTwo);

        if (len == 0) {
            buf.setLength(45);
        } else {
            buf.setLength(len);
        }

        return buf;
    }

    /** ************************************************************
     *  write() Writes a record to the specified RandomAccessFile.
     *          1- First it writes a int (recid) to the output file
     *          2- Next it writes the quantity as an int.
     *          3- Then it writes the remaing record as a string.
     ************************************************************** */
    public void write(RandomAccessFile file) throws IOException {
        StringBuffer buf = new StringBuffer(" ");
        String str = "", str2 = "";

        file.writeInt(recID);

        file.writeInt(quantity);

        str = str + toolType + ";;";

        str = str + brandName + ";;";
        str = str + partNum + ";;";
        str = str + cost + ";;";

        str = str + toolDesc + ";;;";


        buf = fill(str, buf.delete(0, 451), 451);

        file.writeChars(buf.toString());
        sysPrint("write(): - The value of recID is " + recID);
        sysPrint("write(): - The value of quantity is " + quantity);
        sysPrint("write(): - The value of str2 is " + str + " with a length of "
                + str2.length());
        sysPrint("write(): - The length of buf is " + buf.length());

    }


    /** ********************************************************
     * Method: writeInteger() is used to wite an integer to the
     *         randomaccess file.
     ********************************************************/
    public void writeInteger(RandomAccessFile file, int a) throws IOException {
        file.writeInt(a);
    }

    public int getRecID() {
        return recID;
    }

    public String getToolType() {
        return toolType.trim();
    }

    public String getToolDesc() {
        return toolDesc.trim();
    }

    public String getPartNumber() {
        return partNum.trim();
    }

    public int getQuantity() {
        return quantity;
    }

    public String getBrandName() {
        return brandName.trim();
    }

    public String getCost() {
        return cost.trim();
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public void setRecID(int recID) {
        this.recID = recID;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setToolDesc(String toolDesc) {
        this.toolDesc = toolDesc;
    }

    public void setPartNumber(String partNumber) {
        partNum = partNumber;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setFilePos(long filePos) {
        this.filePos = filePos;
    }

    public void setFileLen(long fileLen) {
        this.fileLen = fileLen;
    }

    public void sysPrint(String str) {
        if (myDebug) {
            System.out.println(str);
        }
    }

    public static int getSize() {
        return 585;
    }
}
