IDENTIFICATION DIVISION.
PROGRAM-ID. OCCURS-TEST.

DATA DIVISION.
WORKING-STORAGE SECTION.
  01 WS-ARRAY OCCURS 3 TIMES.
     05 WS-HELLO PIC A(10) VALUE 'HELLO'.
     05 WS-TEST PIC A(5) value 'Test'.
     05 WS-END PIC A(5) VALUE 'ENDE'.

PROCEDURE DIVISION.
MOVE 'A' to WS-TEST(1).
MOVE 'B' TO WS-TEST(2).
MOVE 'C' TO WS-TEST(3).
DISPLAY WS-ARRAY(1) '#'.
DISPLAY WS-ARRAY(2) '#'.
DISPLAY WS-ARRAY(3) '#'.
STOP RUN.