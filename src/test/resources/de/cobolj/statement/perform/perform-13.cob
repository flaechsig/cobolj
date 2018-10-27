IDENTIFICATION DIVISION.
PROGRAM-ID. HELLO.

DATA DIVISION.
   WORKING-STORAGE SECTION.
   01 WS-TMP PIC S9(2). 

PROCEDURE DIVISION.
    Perform 
    test after Varying ws-tmp from -9 by 2
    until ws-tmp is positive
        Display ws-tmp
    end-perform.

    display ws-tmp
STOP RUN.