IDENTIFICATION DIVISION.
PROGRAM-ID. HELLO-WORLD.


DATA DIVISION.
   WORKING-STORAGE SECTION.
   01 WS-RESULT PIC S9(1).
   
PROCEDURE DIVISION.
    COMPUTE WS-RESULT = 5 - 2 - 1
    DISPLAY WS-RESULT

STOP RUN.