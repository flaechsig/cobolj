IDENTIFICATION DIVISION.
PROGRAM-ID. CALL2-1.

DATA DIVISION.
   WORKING-STORAGE SECTION.
   01 WS-VALUE-1 PIC 9(1) VALUE 1.

PROCEDURE DIVISION.
   	DISPLAY WS-VALUE-1.
	CALL 'CALL2-1-1' USING WS-VALUE-1.
   	DISPLAY WS-VALUE-1.
STOP RUN.