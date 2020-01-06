IDENTIFICATION DIVISION.
PROGRAM-ID. HELLO-WORLD.

data division.
working-storage section.
  01 ws-test pic xxbxx.

PROCEDURE DIVISION.
move 'abcd' to ws-test.
DISPLAY ws-test.
STOP RUN.