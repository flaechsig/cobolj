IDENTIFICATION DIVISION. 
    PROGRAM-ID. HELLO-WORLD.
    
DATA DIVISION.
WORKING-STORAGE SECTION.
  01 WS-QUELLE PIC X(25) VALUE '00001##Max#Muster'.
  01 WS-SENKE.
     05 WS-NUMMER PIC A(5).
     05 WS-VORNAME PIC A(10).
     05 WS-NACHNAME PIC A(10).
  
PROCEDURE DIVISION.
  UNSTRING WS-QUELLE
    DELIMITED BY '#'
    INTO WS-NUMMER WS-VORNAME WS-NACHNAME.
  DISPLAY WS-NUMMER.
  DISPLAY WS-VORNAME.
  DISPLAY WS-NACHNAME.
  DISPLAY WS-QUELLE.
  STOP RUN.