IDENTIFICATION DIVISION. 
    PROGRAM-ID. HELLO-WORLD.
    
DATA DIVISION.
  LINKAGE SECTION.
  01 LS-HELLO PIC A(10).

PROCEDURE DIVISION USING LS-HELLO.
    DISPLAY 'Hello ' LS-HELLO.
EXIT PROGRAM.