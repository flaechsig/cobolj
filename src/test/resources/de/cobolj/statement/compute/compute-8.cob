IDENTIFICATION DIVISION.
PROGRAM-ID. HELLO-WORLD.


DATA DIVISION.
   WORKING-STORAGE SECTION.
   01 WS-RESULT1 PIC S9(1).
   01 WS-RESULT2 PIC S9(1).
   
PROCEDURE DIVISION.
    COMPUTE WS-RESULT1 WS-RESULT2 = 5 - 2 - 1
    DISPLAY WS-RESULT1 " " WS-RESULT2

STOP RUN.