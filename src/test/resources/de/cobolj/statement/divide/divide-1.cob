IDENTIFICATION DIVISION.
PROGRAM-ID. HELLO.

DATA DIVISION.
   WORKING-STORAGE SECTION.
   01 WS-NUMBER-RESULT-1 PIC 9(3) VALUE 51.
   01 WS-NUMBER-RESULT-2 PIC 9(3) VALUE 101.

PROCEDURE DIVISION.
   divide 2 INTO WS-NUMBER-RESULT-1 WS-NUMBER-RESULT-2 ROUNDED
   NOT ON SIZE ERROR DISPLAY "KEIN SPEICHERUEBERLAUF".
   DISPLAY WS-NUMBER-RESULT-1.
   DISPLAY WS-NUMBER-RESULT-2.
STOP RUN.
