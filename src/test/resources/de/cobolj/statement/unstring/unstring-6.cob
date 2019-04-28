IDENTIFICATION DIVISION. 
    PROGRAM-ID. HELLO-WORLD.
    
DATA DIVISION.
WORKING-STORAGE SECTION.
  01 WS-QUELLE PIC X(25) VALUE '00001#Max;Muster'.
  01 WS-SENKE.
     05 WS-NUMMER PIC A(5).
     05 WS-VORNAME PIC A(10).
     05 WS-NACHNAME PIC A(10).
     05 WS-VORNAME-DELIM PIC A(1).
     05 WS-VORNAME-COUNT PIC 9(1).
  
PROCEDURE DIVISION.
  UNSTRING WS-QUELLE
    DELIMITED BY ALL '#'
           OR ALL ';'
    INTO WS-NUMMER 
         WS-VORNAME DELIMITER WS-VORNAME-DELIM COUNT WS-VORNAME-COUNT
         WS-NACHNAME.
  DISPLAY WS-NUMMER.
  DISPLAY WS-VORNAME ' ' WS-VORNAME-DELIM ' ' WS-VORNAME-COUNT.
  DISPLAY WS-NACHNAME.
  DISPLAY WS-QUELLE '#'.
  STOP RUN.