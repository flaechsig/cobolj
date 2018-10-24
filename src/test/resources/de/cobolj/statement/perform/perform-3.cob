IDENTIFICATION DIVISION.
PROGRAM-ID. HELLO.

DATA DIVISION.
   WORKING-STORAGE SECTION.
   01 WS-COUNTER PIC 9(1) VALUES 5.
   
PROCEDURE DIVISION.
    001-para.
        Display "Hello"
        Perform 002-para.
        Display "Hello3".
        Stop run.
        
    002-para.
        display "Hello2".
        