IDENTIFICATION DIVISION.
PROGRAM-ID. HELLO.

DATA DIVISION.
   WORKING-STORAGE SECTION.
   01 A   PIC 9(5) VALUE 12345.
   01 B REDEFINES A PIC 99V999.
   01 C REDEFINES A PIC 999V99.

PROCEDURE DIVISION.
   DISPLAY A.
   DISPLAY B.
   DISPLAY C.
STOP RUN.