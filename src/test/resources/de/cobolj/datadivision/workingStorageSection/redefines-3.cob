IDENTIFICATION DIVISION.
PROGRAM-ID. PERFTIMI.
ENVIRONMENT DIVISION.
DATA DIVISION.
WORKING-STORAGE SECTION.
  01 WS-NAME.
     05 WS-FST-NAME PIC X(5) VALUE 'HANS'.
     05 WS-SEC-NAME PIC X(10) VALUE 'FLAECHSIG'.
  01 WS-TOTAL-NAME REDEFINES WS-NAME PIC X(15).
  
PROCEDURE DIVISION.
	DISPLAY 'ACTUAL DATA IN WS-NAME : ' WS-NAME.
	DISPLAY 'ACTUAL DATA IN WS-TOTAL-NAME: ' WS-TOTAL-NAME.
	
	STOP RUN.