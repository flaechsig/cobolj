IDENTIFICATION DIVISION.
PROGRAM-ID. HELLO.

DATA DIVISION.
   WORKING-STORAGE SECTION.
   01 WS-NUMBER-1 PIC 9(3) VALUE 50.
   01 WS-NUMBER-2 PIC 9(3)V9(2) VALUE 100.50.
   01 WS-NUMBER-RESULT PIC 9(4).

PROCEDURE DIVISION.
   ADD 5 5.1 WS-NUMBER-1 TO WS-NUMBER-RESULT
   DISPLAY WS-NUMBER-RESULT
STOP RUN.