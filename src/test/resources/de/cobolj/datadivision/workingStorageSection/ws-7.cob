IDENTIFICATION DIVISION.
PROGRAM-ID. HELLO.

DATA DIVISION.
   WORKING-STORAGE SECTION.
   01 WS-ID   PIC S9(7) VALUE -12345.

PROCEDURE DIVISION.
   DISPLAY "My ID is : " WS-ID.
STOP RUN.