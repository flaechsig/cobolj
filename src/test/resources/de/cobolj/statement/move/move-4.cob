IDENTIFICATION DIVISION.
PROGRAM-ID. HELLO-WORLD.

DATA DIVISION.
  WORKING-STORAGE SECTION.
  01 WS-GROUP1.
     02 WS-ITEM1 PIC X(2) VALUE 'AB'.
     02 WS-ITEM2 PIC X(2) VALUE 'CD'.
  01 WS-GROUP2.
     02 WS-ITEM1 PIC X(2) VALUE '12'.
     02 WS-ITEM1 PIC X(2).
     
PROCEDURE DIVISION.
DISPLAY WS-GROUP1 " " WS-GROUP2.
MOVE WS-GROUP1 TO WS-GROUP2.
DISPLAY WS-GROUP1 " " WS-GROUP2.
STOP RUN.