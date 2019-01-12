IDENTIFICATION DIVISION.
PROGRAM-ID. HELLO.

DATA DIVISION.
   WORKING-STORAGE SECTION.
   01 WS-NUMBER-RESULT-1 PIC 9(3) VALUE 50.
   01 WS-NUMBER-RESULT-2 PIC 9(3) VALUE 100.

PROCEDURE DIVISION.
   MULTIPLY 2 BY WS-NUMBER-RESULT-1 WS-NUMBER-RESULT-2
   NOT ON SIZE ERROR DISPLAY "KEIN SPEICHERUEBERLAUF".
   DISPLAY WS-NUMBER-RESULT-1.
   DISPLAY WS-NUMBER-RESULT-2.
STOP RUN.
