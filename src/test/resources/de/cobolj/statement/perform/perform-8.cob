IDENTIFICATION DIVISION.
PROGRAM-ID. HELLO-WORLD.


DATA DIVISION.
   WORKING-STORAGE SECTION.
   01 WS-COUNTER PIC S9(1) VALUES -3.
   
PROCEDURE DIVISION.

    Perform test before until ws-counter is not negative
        add 1 to ws-counter
        display "Hello " ws-counter
    end-perform.

STOP RUN.