IDENTIFICATION DIVISION.
PROGRAM-ID. HELLO-WORLD.


DATA DIVISION.
   WORKING-STORAGE SECTION.
   01 WS-RESULT1 PIC S9(2) VALUE 0.
   01 WS-RESULT2 PIC S9(2).
   
PROCEDURE DIVISION.
    COMPUTE WS-RESULT1 Rounded WS-RESULT2= 5 + 6.5
    DISPLAY WS-RESULT1 " " WS-RESULT2

STOP RUN.