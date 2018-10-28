IDENTIFICATION DIVISION.
PROGRAM-ID. HELLO-WORLD.
   
PROCEDURE DIVISION.
    if 1 < 2 THEN
      Next Sentence
    ELSE
      DISPLAY "False"
    END-IF
    DISPLAY "After If".
    DISPLAY "Next Sentence"
STOP RUN.