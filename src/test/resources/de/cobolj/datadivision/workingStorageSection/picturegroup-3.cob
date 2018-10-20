IDENTIFICATION DIVISION.
PROGRAM-ID. HELLO.

DATA DIVISION.
   WORKING-STORAGE SECTION.
   01 WS-GROUP.
      02 ITEM-1 PIC A(10) Value 'Hello'.
      02 ITEM-2 PIC A(10) Value 'World'.
      02 NEXT-GROUP.
         03 ITEM-3 PIC A(5) Value '12345'.
      02 THIRD-GROUP.
         03 ITEM-4 PIC A(5) Value 'abcde'.
         03 ITEM-5 PIC A(2) Value '12'.

PROCEDURE DIVISION.
   DISPLAY WS-GROUP.
   Display NEXT-Group.
   Display Third-Group.
   DISPLAY ITEM-1.
   DISPLAY ITEM-2.
   DISPLAY ITEM-3.
   Display Item-4.
   Display Item-5.
STOP RUN.