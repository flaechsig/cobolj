IDENTIFICATION DIVISION. 
    PROGRAM-ID. HELLO-WORLD.
    
DATA DIVISION.
  LINKAGE SECTION.
  01 LS-VALUE-1 PIC 9(2).
  01 LS-VALUE-2 PIC 9(2).

PROCEDURE DIVISION USING LS-VALUE-1 LS-VALUE-2.
    DISPLAY LS-VALUE-1 ' ' LS-VALUE-2.
	ADD 1 TO LS-VALUE-1.
	ADD 1 TO LS-VALUE-2.
    DISPLAY LS-VALUE-1 ' ' LS-VALUE-2.
EXIT PROGRAM.