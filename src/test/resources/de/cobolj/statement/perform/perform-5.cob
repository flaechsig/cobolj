
IDENTIFICATION DIVISION.
PROGRAM-ID. HELLO.

DATA DIVISION.
   WORKING-STORAGE SECTION.
   01 WS-COUNTER PIC 9(1) VALUES 5.
   
PROCEDURE DIVISION.
    001-para.
        Display "001-para"
        Perform 003-para thru 005-para 2 times.
        
    002-para.
        display "002-para".
        Stop Run.
        
    003-para.
        display "003-para".
        
    004-para.
        display "004-para".
        
    005-para.
        display "005-para".