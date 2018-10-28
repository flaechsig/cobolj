IDENTIFICATION DIVISION.
PROGRAM-ID. HELLO.

DATA DIVISION.
   WORKING-STORAGE SECTION.
   01 WS-TMP1 PIC S9(2). 
   01 WS-TMP2 PIC S9(2). 

PROCEDURE DIVISION.
PERFORM
       VARYING ws-tmp1 FROM -2 BY 1 UNTIL ws-tmp1 is positive
         AFTER ws-tmp2 FROM -2 BY 1 UNTIL ws-tmp2 is positive
            DISPLAY ws-tmp1 " " ws-tmp2
END-PERFORM

display ws-tmp1 " " ws-tmp2
    
STOP RUN.