IDENTIFICATION DIVISION.
PROGRAM-ID. HELLO.

DATA DIVISION.
   WORKING-STORAGE SECTION.
   01 WS-COUNTER PIC 9(1) VALUES 5.
   
PROCEDURE DIVISION.
   Perform WS-COUNTER Times
    Display 'Hello'
   End-Perform. 
STOP RUN.