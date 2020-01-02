IDENTIFICATION DIVISION.
PROGRAM-ID. PERFTIMI.
ENVIRONMENT DIVISION.
DATA DIVISION.
WORKING-STORAGE SECTION.
  01 Birth-Date-YYYYMMDD    pic 9(8) value 19991212.
  01 Birth-Data-Separate redefines Birth-Date-YYYYMMDD.
     05 Birth-Date-YYYY     pic 9(4).
     05 Birth-Date-MM       pic 99.
     05 Birth-Date-DD       pic 99.
  
PROCEDURE DIVISION.
	DISPLAY Birth-Date-YYYYMMDD.
	DISPLAY Birth-Date-YYYY.
	DISPLAY Birth-Date-MM.
	DISPLAY Birth-Date-DD.
	
	STOP RUN.