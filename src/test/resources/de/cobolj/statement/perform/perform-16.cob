IDENTIFICATION DIVISION.
PROGRAM-ID. HELLO.

DATA DIVISION.
   WORKING-STORAGE SECTION.
   01 WS-TMP1 PIC S9(2).
   01 WS-TMP2 PIC S9(2).  
   01 WS-TMP3 PIC S9(2).  

PROCEDURE DIVISION.
PERFORM
       TEST AFTER VARYING ws-tmp1 FROM -1 BY 1 UNTIL ws-tmp1 is positive
         AFTER ws-tmp2 FROM -1 BY 1 UNTIL ws-tmp2 is positive
         AFTER ws-tmp3 FROM -1 BY 1 UNTIL ws-tmp3 is positive
            DISPLAY ws-tmp1 " " ws-tmp2 " " ws-tmp3
END-PERFORM

display ws-tmp1 " " ws-tmp2 " " ws-tmp3
    
STOP RUN.