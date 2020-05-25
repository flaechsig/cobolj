IDENTIFICATION DIVISION.
PROGRAM-ID. HELLO-WORLD.

Data division.
working-storage section.
  01 ws-group.
     05 ws-part1 pic 9V9 value 1.1.
     05 ws-part2 pic 99 value 22.
     
PROCEDURE DIVISION.

DISPLAY '#' ws-group'#'.
STOP RUN.