IDENTIFICATION DIVISION.
PROGRAM-ID. HELLO-WORLD.

DATA DIVISION.
WORKING-STORAGE SECTION.
  01 WS-TEST PIC Z(4) value 99.
    

PROCEDURE DIVISION.
DISPLAY WS-TEST.
STOP RUN.