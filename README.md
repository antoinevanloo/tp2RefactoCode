Copyright (c) 2002-2003 Advanced Applications Total Applications Works.
  (AATAW)  All Rights Reserved.
 
  *AATAW grants you ("Licensee") a non-exclusive, royalty free, license to use,
  modify and redistribute this software in source and binary code form,
  provided that i) this copyright notice and license appear on all copies of
  the software; and ii) Licensee does not utilize the software in a manner
  which is disparaging to AATAW.*
 
  *This software is provided "AS IS," without a warranty of any kind. ALL
  EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
  IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
  NON-INFRINGEMENT, ARE HEREBY EXCLUDED. AATAW AND ITS LICENSORS SHALL NOT BE
  LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
  OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL AATAW OR ITS
  LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
  INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
  CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
  OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
  POSSIBILITY OF SUCH DAMAGES.*
 
  *This software is not designed or intended for use in on-line control of
  aircraft, air traffic, aircraft navigation or aircraft communications; or in
  the design, construction, operation or maintenance of any nuclear
  facility. Licensee represents and warrants that it will not use or
  redistribute the Software for such purposes.*


**FILES included:**
1. HardwareStore.java
2. Record.java
3. HW_Tutorial.html            
4. README.md
5. Help_doc.html                
6. CompileRun_HardwareStore - use this .bat file to compile and run the Hardware Store
    program
7. Run_HardwareStore –  use this to run the program if it has been successfully compiled
8. run_Javadoc – use this to create program documentation

**How to Use:**
Double clicking on an item on the main frame will cause the Update Dialog to be displayed.

The code accepts the userid of ***admin*** and the password of ***hwstore***.
 
 **If you want to disable the PassWord Dialog, change the code on line 1336 from**

     if ( ( uID.equals("admin") ) &&
                  ( pwd.equals("hwstore") ) ) {

---------------------------- to -----------------------------

    if ( ( uID.equals("") ) &&
                  ( pwd.equals("") ) ) {

and pressed Enter when the Passwrod Dialog appears.


Changes:

1- 7/03/2003
   - I changed the coded System.out.println to sysPrint (" message " ) which the 
     user can toggle on or off by changing the value of private boolean myDebug = false 
     on line 95.
