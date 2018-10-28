IDENTIFICATION DIVISION.
PROGRAM-ID. HELLO-WORLD.
   
PROCEDURE DIVISION.
    if 2 < 2 THEN
      Display "Hello"
    ELSE
      Next Sentence
    END-IF
    DISPLAY "After If".
    DISPLAY "Next Sentence"
STOP RUN.