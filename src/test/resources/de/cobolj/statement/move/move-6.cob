IDENTIFICATION DIVISION.
PROGRAM-ID. HELLO-WORLD.

DATA DIVISION.
  WORKING-STORAGE SECTION.
  01 WS-ITEM1 PIC S9(2).

PROCEDURE DIVISION.
move 1 to ws-item1.
display ws-item1.
move 123 to ws-item1.
display ws-item1.
move +1 to ws-item1.
display ws-item1.
move +123 to ws-item1.
display ws-item1.
move -1 to ws-item1.
display ws-item1.
move -123 to ws-item1.
display ws-item1.
move 0 to ws-item1.
display ws-item1.
STOP RUN.