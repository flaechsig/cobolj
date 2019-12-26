 IDENTIFICATION DIVISION.                                         
                                                                  
 PROGRAM-ID.                                                      
     EXEC85.                                                      
 INSTALLATION.                                                    
     "ON-SITE VALIDATION, NATIONAL INSTITUTE OF STD & TECH.     ".
     "COBOL 85 VERSION 4.2, Apr  1993 SSVG                      ".
 ENVIRONMENT DIVISION.                                            
                                                                  
 CONFIGURATION SECTION.                                           
                                                                  
 SPECIAL-NAMES.                                                   
 INPUT-OUTPUT SECTION.                                            
 FILE-CONTROL.                                                    
     SELECT  OPTIONAL POPULATION-FILE                             
     ASSIGN TO                                                    
     XXXXX001.                                                    
     SELECT  SOURCE-COBOL-PROGRAMS                                
     ASSIGN TO                                                    
     XXXXX002                                                     
     ORGANIZATION SEQUENTIAL.                                     
     SELECT  UPDATED-POPULATION-FILE                              
     ASSIGN TO                                                    
     XXXXX003.                                                    
     SELECT  PRINT-FILE                                           
     ASSIGN TO                                                    
     XXXXX055.                                                    
     SELECT  CONTROL-CARD-FILE                                    
     ASSIGN TO                                                    
     XXXXX058.                                                    
 DATA DIVISION.                                                   
 FILE SECTION.                                                    
 FD  POPULATION-FILE.                                             
*>    RECORD CONTAINS 2400 CHARACTERS.                             
 01  SOURCE-IN-2400.                                              
     02 SOURCE-IN                    PIC X(80).                   
*>                                              OCCURS 30.         
 FD  CONTROL-CARD-FILE.                                           
 01  CONTROL-RECORD                  PIC X(80).                   
 FD  PRINT-FILE.                                                  
 01  PRINT-REC.                                                   
   05        FILLER                  PIC X.                       
   05        PRINT-DATA              PIC X(131).                  
 FD  SOURCE-COBOL-PROGRAMS                                        
     BLOCK CONTAINS 1 RECORDS.                                    
 01  CT-OUT.                                                      
     02 FILLER PIC X(72).                                         
     02 FILLER PIC X(8).                                          
 FD  UPDATED-POPULATION-FILE                                      
     RECORD CONTAINS 2400 CHARACTERS.                             
 01  UPDATED-SOURCE-OUT-2400.                                     
     02 UD-SOURCE-OUT                PIC X(80)  OCCURS 30.        
                                                                  
 WORKING-STORAGE SECTION.                                         
                                                                  
 01  FILLER                          PIC X(40)  VALUE             
            "NEWEXEC WORKING-STORAGE STARTS HERE ==->".           
 01  BLOCK-TYPE                      PIC X(5).                    
 01  SUB1                            PIC S9(3)  COMP.             
 01  SUB2                            PIC S9(3)  COMP.             
 01  SUB3                            PIC S9(3)  COMP.             
 01  SUB4                            PIC S9(3)  COMP.             
 01  SUB5                            PIC S9(3)  COMP.             
 01  SUB6                            PIC S9(3)  COMP.             
 01  SUB7                            PIC S9(3)  COMP.             
 01  WA-ERR-IND                      PIC 9 VALUE ZEROES.          
 01  WA-FIRST-IND                    PIC 9 VALUE ZEROES.          
 01  WA-ZCARD-TABLE.                                              
   05        WA-ZCARD                OCCURS 10                    
                                     PIC X(60).                   
 01  WA-TOP-OF-PAGE-LINE.                                         
   05        FILLER                  PIC X(4)   VALUE SPACES.     
   05        WA-VERSION.                                          
     07      WA-VERSION-TEXT         PIC X(22)  VALUE             
            "CCVS85 VERSION NUMBER ".                             
     07      WA-VERSION-NUM          PIC X(3) VALUE SPACES.       
   05        WA-RELEASE.                                          
     07      WA-RELEASE-TEXT         PIC X(14)  VALUE             
            ", RELEASED ON ".                                     
     07      WA-VERSION-DATE         PIC X(11) VALUE SPACES.      
   05        FILLER                  PIC X(4)   VALUE SPACES.     
   05        WA-COMPANY-AND-COMPILER PIC X(30) VALUE SPACES.      
   05        FILLER                  PIC X(5)   VALUE SPACES.     
   05        WA-DATE                 PIC XXBXXBXX.                
   05        FILLER                  PIC X(4)   VALUE SPACES.     
   05        FILLER                  PIC X(5)   VALUE "PAGE ".    
   05        WA-PAGE-CT              PIC Z(5)9.                   
                                                                  
 01  WA-ACCT-LINE-1.                                              
   05        FILLER                  PIC X(19)  VALUE             
            " ** END OF PROGRAM ".                                
   05        WA-CURRENT-PROG         PIC X(6).                    
   05        FILLER                  PIC X(32)  VALUE             
            " FOUND,  COBOL LINES PROCESSED: ".                   
   05        WA-LINES-COBOL          PIC Z(5)9.                   
 01  WA-ACCT-LINE-2.                                              
   05        FILLER                  PIC X(19)  VALUE             
            " ** LINES INSERTED ".                                
   05        WA-LINES-INSERTED       PIC Z(5)9.                   
   05        FILLER                  PIC X(19)  VALUE             
            " ** LINES REPLACED ".                                
   05        WA-LINES-REPLACED       PIC Z(5)9.                   
   05        FILLER                  PIC X(19)  VALUE             
            " ** LINES DELETED  ".                                
   05        WA-LINES-DELETED        PIC Z(5)9.                   
 01  WA-ACCT-LINE-3.                                              
   05        FILLER                  PIC X(18)  VALUE             
            " ** OPTIONAL CODE ".                                 
   05        WA-OPTIONAL-CODE        PIC X(8).                    
   05        WA-CODE-REMOVED         PIC Z(5)9.                   
   05        WA-CODE-KILLED          PIC X(21)  VALUE             
            " ** COMMENTS DELETED ".                              
   05        WA-COMMENTS-DEL         PIC Z(5)9.                   
 01  WA-FINAL-LINE-1.                                             
   05        FILLER                  PIC X(34)  VALUE             
            " ** END OF POPULATION FILE REACHED".                 
   05        FILLER                  PIC X(27)  VALUE             
            " NUMBER OF PROGRAMS FOUND: ".                        
   05        WA-PROGS-FOUND          PIC Z(5)9.                   
 01  WA-FINAL-LINE-2.                                             
   05        FILLER                  PIC X(47)  VALUE             
            " ** NUMBER OF PROGRAMS WRITTEN TO SOURCE FILE: ".    
   05        WA-SOURCE-PROGS         PIC Z(5)9.                   
 01  WA-FINAL-LINE-3.                                             
   05        FILLER                  PIC X(48)  VALUE             
            " ** NUMBER OF PROGRAMS WRITTEN TO NEW POPULATION".   
   05        FILLER                  PIC X(7)   VALUE " FILE: ".  
   05        WA-NEWPOP-PROGS         PIC Z(5)9.                   
 01  WB-CONTROL-DATA.                                             
   05        WB-FILL                 PIC X(80).                   
   05        FILLER                  REDEFINES  WB-FILL.          
     10      WB-3                    PIC X(3).                    
     10      FILLER                  PIC X(77).                   
   05        FILLER                  REDEFINES  WB-FILL.          
     10      WB-4                    PIC X(4).                    
     10      WB-NN                   PIC 99.                      
     10      FILLER                  PIC X.                       
     10      WB-X                    PIC X.                       
     10      FILLER                  PIC X(72).                   
   05        FILLER                  REDEFINES  WB-FILL.          
     10      WB-6                    PIC X(6).                    
     10      FILLER                  PIC X(74).                   
   05        FILLER                  REDEFINES  WB-FILL.          
     10      WB-7                    PIC X(7).                    
     10      FILLER                  PIC X(73).                   
   05        FILLER                  REDEFINES  WB-FILL.          
     10      WB-8                    PIC X(8).                    
     10      FILLER                  PIC X(72).                   
   05        FILLER                  REDEFINES  WB-FILL.          
     10      WB-9                    PIC X(9).                    
     10      FILLER                  PIC X(71).                   
   05        FILLER                  REDEFINES  WB-FILL.          
     10      WB-10                   PIC X(10).                   
     10      FILLER                  PIC X(70).                   
   05        FILLER                  REDEFINES  WB-FILL.          
     10      WB-11                   PIC X(11).                   
     10      FILLER                  PIC X(69).                   
   05        FILLER                  REDEFINES  WB-FILL.          
     10      WB-12                   PIC X(12).                   
     10      FILLER                  PIC X.                       
     10      WB-PROG                 PIC X(5).                    
     10      FILLER                  PIC X(62).                   
   05        FILLER                  REDEFINES  WB-FILL.          
     10      WB-13                   PIC X(13).                   
     10      FILLER                  PIC X(67).                   
   05        FILLER                  REDEFINES  WB-FILL.          
     10      WB-14                   PIC X(14).                   
     10      FILLER                  PIC X.                       
     10      WB-MODULE               PIC XX.                      
     10      FILLER                  PIC X.                       
     10      WB-LEVEL                PIC X.                       
     10      FILLER                  PIC X(61).                   
   05        FILLER                  REDEFINES  WB-FILL.          
     10      WB-15                   PIC X(15).                   
     10      FILLER                  PIC X(65).                   
   05        FILLER                  REDEFINES  WB-FILL.          
     10      WB-16                   PIC X(16).                   
     10      FILLER                  PIC X(64).                   
   05        WB-X-CARD               REDEFINES  WB-FILL.          
     10      WB-X-HYPHEN             PIC XX.                      
     10      WB-X-CARD-NUM           PIC 9(3).                    
     10      WB-PROG-POS.                                         
       15    WB-PROG-POS-NUM         PIC 99.                      
     10      FILLER                  PIC X.                       
     10      WB-SUBS-TEXT            PIC X(60).                   
     10      FILLER                  PIC X(12).                   
   05        WB-START-CARD           REDEFINES  WB-FILL.          
     10      WB-STAR-START           PIC X(6).                    
     10      FILLER                  PIC X.                       
     10      WB-UPDATE-PROG          PIC X(6).                    
     10      FILLER                  PIC X.                       
     10      WB-RENUMBER             PIC X.                       
     10      FILLER                  PIC X(65).                   
   05        WB-LINE-UPDATE          REDEFINES  WB-FILL.          
     10      WB-SEQ-1                PIC X(6).                    
     10      WB-COBOL-LINE           PIC X(74).                   
     10      FILLER                  REDEFINES  WB-COBOL-LINE.    
       15    WB-COL-7                PIC X.                       
       15    FILLER                  PIC X(73).                   
     10      FILLER                  REDEFINES  WB-COBOL-LINE.    
       15    WB-CHAR                 PIC X.                       
       15    WB-SEQ-2                PIC X(6).                    
                                                                 
 01  WC-CURRENT-POP-RECORD.                                       
   05        WC-1.                                                
     10      WC-END-OF-POPFILE       PIC X(16).                   
     10      FILLER                  PIC X(64).                   
   05        WC-HEADER               REDEFINES WC-1.              
     10      WC-STAR-HEADER          PIC X(7).                    
     10      FILLER                  PIC X.                       
     10      WC-COBOL                PIC X(5).                    
     10      FILLER                  PIC X.                       
     10      WC-PROG-ID.                                          
      12     WC-PROG-ID-1-5.                                      
       15    WC-PROG-ID-1-4.                                      
        18   WC-MODULE               PIC XX.                      
        18   WC-LEVEL                PIC X.                       
        18   FILLER                  PIC X.                       
       15    FILLER                  PIC X.                       
      12     WC-PROG-ID-6            PIC X.                       
     10      FILLER                  PIC X.                       
     10      WC-SUBPRG               PIC X(6).                    
     10      FILLER                  PIC X.                       
     10      WC-PROG2ID.                                          
      12     WC-PROG2ID-1-5          PIC X(5).                    
      12     FILLER                  PIC X.                       
     10      FILLER                  PIC X(46).                   
   05        FILLER                  REDEFINES WC-1.              
     10      WC-1-72.                                             
       15    WC-6.                                                
         20  WC-STAR                 PIC X.                       
         20  FILLER                  PIC X(5).                    
       15    FILLER                  REDEFINES  WC-6.             
         20  WC-1-5                  PIC X(5).                    
         20  FILLER                  PIC X.                       
       15    WC-COL-7                PIC X.                       
       15    WC-COL-8                PIC X.                       
       15    FILLER                  PIC X(3).                    
       15    WC-SUB-DATA.                                         
         20  WC-12-15                PIC X(4).                    
         20  FILLER                  PIC X.                       
         20  WC-17-19                PIC 9(3).                    
         20  WC-20                   PIC X.                       
         20  FILLER                  PIC X(52).                   
     10      WC-73-80                PIC X(8).                    
                                                                  
 01  WD-SOURCE-REC.                                               
   05        WD-1.                                                
     10      FILLER                  PIC X(6).                    
     10      WD-HEADER               PIC X(74).                   
                                                                  
 01  WE-PRINT-DATA.                                               
   05        WE-COBOL-LINE           PIC X(80).                   
   05        FILLER                  PIC X      VALUE SPACE.      
   05        WE-X-CARD               PIC X(9).                    
   05        FILLER                  PIC XX     VALUE SPACES.     
   05        WE-CHANGE-TYPE          PIC X(12).                   
                                                                  
 01  WF-PROGRAM-SELECTED-TABLE.                                   
   05        WF-PROGRAM-SELECTED     PIC X(5)   OCCURS 50.        
                                                                  
 01  WG-MODULE-SELECTED-TABLE.                                    
   05        FILLER                             OCCURS 10.        
     10      WG-MODULE-SELECTED      PIC XX.                      
     10      WG-MODULE-LEVEL         PIC X.                       
                                                                  
 01  WV-PRINT-MISCELLANEOUS.                                      
   05        WV-OPTION-HEADING       PIC X(25)  VALUE             
            " OPTION SWITCH SETTINGS -".                          
   05        WV-OPT-1                PIC X(40)  VALUE             
         " 0                 1                   2".              
   05        WV-OPT-2                PIC X(52)  VALUE             
         " 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6".  
   05        WV-OPT-SWITCHES.                                     
     10      FILLER                  PIC X      VALUE SPACE.      
     10      FILLER                  OCCURS 26.                   
       15    WV-OPT                  PIC X.                       
       15    FILLER                  PIC X.                       
 01  WX-X-CARD-TABLE.                                             
   05        WX-X-CARD               OCCURS 200.                  
     10      WX-X-CHAR               PIC X                        
                                     OCCURS 60.                   
 01  WX-PROG-POS-TABLE.                                           
   05        WX-PROG-POS             OCCURS 200                   
                                     PIC 99.                      
 01  WY-SWITCHES.                                                 
   05        WY-OPTION-SWITCHES.                                  
     10      WY-OPT-SW-1             PIC X.                       
     10      WY-OPT-SW-2             PIC X.                       
     10      WY-OPT-SW-3             PIC X.                       
     10      WY-OPT-SW-4             PIC X.                       
     10      WY-OPT-SW-5             PIC X.                       
     10      WY-OPT-SW-6             PIC X.                       
     10      WY-OPT-SW-7             PIC X.                       
     10      WY-OPT-SW-8             PIC X.                       
     10      WY-OPT-SW-9             PIC X.                       
     10      WY-OPT-SW-10            PIC X.                       
     10      WY-OPT-SW-11            PIC X.                       
     10      WY-OPT-SW-12            PIC X.                       
     10      WY-OPT-SW-13            PIC X.                       
     10      WY-OPT-SW-14            PIC X.                       
     10      WY-OPT-SW-15            PIC X.                       
     10      WY-OPT-SW-16            PIC X.                       
     10      WY-OPT-SW-17            PIC X.                       
     10      WY-OPT-SW-18            PIC X.                       
     10      WY-OPT-SW-19            PIC X.                       
     10      WY-OPT-SW-20            PIC X.                       
     10      WY-OPT-SW-21            PIC X.                       
     10      WY-OPT-SW-22            PIC X.                       
     10      WY-OPT-SW-23            PIC X.                       
     10      WY-OPT-SW-24            PIC X.                       
     10      WY-OPT-SW-25            PIC X.                       
     10      WY-OPT-SW-26            PIC X.                       
   05        FILLER                  REDEFINES WY-OPTION-SWITCHES.
     10      WY-OPT-SW               PIC X                        
                                     OCCURS 26.                   
   05        WY-PRINT-SWITCHES.                                   
     10      WY-EXTRACT-ALL          PIC X.                       
     10      WY-EXTRACT-AUTO         PIC X.                       
     10      WY-EXTRACT-MAN          PIC X.                       
     10      WY-KILL-DELETIONS       PIC X.                       
     10      WY-LIST-NO-UPDATES      PIC X.                       
     10      WY-LIST-X-CARDS         PIC X.                       
     10      WY-LIST-PROGRAMS        PIC X.                       
     10      WY-LIST-COMPACT         PIC X.                       
     10      WY-NO-DATA              PIC X.                       
     10      WY-NO-LIBRARY           PIC X.                       
     10      WY-NO-SOURCE            PIC X.                       
     10      WY-REMOVE-COMMENTS      PIC X.                       
     10      WY-NEW-POP              PIC X.                       
     10      WY-SELECT-PROG          PIC X.                       
     10      WY-SELECT-MODULE        PIC X.                       
     10      WY-SELECT-LEVEL         PIC X.                       
                                                                  
 01  WZ-MISCELLANEOUS.                                            
   05        WZ-PROGRAM-SELECTED     PIC X.                       
   05        WZ-END-OF-POPFILE       PIC X.                       
   05        WZ-FULL-STOP            PIC X.                       
   05        WZ-DONT-READ-POPFILE    PIC X.                       
   05        WZ-UPDATE-THIS-PROG     PIC X.                       
   05        WZ-REPLACE-FLAG         PIC X.                       
   05        WZ-LINE-UPDATE          PIC X.                       
   05        WZ-RESEQUENCE-THIS      PIC X.                       
   05        WZ-RESEQUENCE-NEXT      PIC X.                       
   05        WZ-END-OF-UPDATES       PIC X.                       
   05        WZ-OPTIONAL-SELECTED    PIC X.                       
   05        WZ-DELETE-FLAG          PIC X.                       
   05        WZ-NOT-THIS-COMMENT     PIC X.                       
   05        WZ-CURRENT-HEADER       PIC X(5).                    
   05        WZ-INVALID-DATA.                                     
     10      FILLER                  PIC X(20).                   
     10      WZ-ERROR-MESSAGE        PIC X(60).                   
   05        WZ-CURRENT-UPD-PROG.                                 
     10      WZ-UPD-PROG-CHAR        PIC X.                       
     10      FILLER                  PIC X(5).                    
   05        WZ-CURRENT-MAIN-PROG.                                
     10      WZ-MAIN-PROG-CHAR       PIC X      OCCURS 6.         
   05        WZ-PROG-BREAK.                                       
     10      WZ-1CHAR                PIC X      OCCURS 6.         
   05        WZ-CURRENT-POP-PROG.                                 
     10      FILLER                  PIC X(5).                    
     10      WZ-PROG-ID-6            PIC X.                       
   05        WZ-MAIN-PROG-FLAG       PIC X.                       
   05        WZ-LINES-COBOL          PIC 9(6).                    
   05        WZ-LINES-INSERTED       PIC 9(6).                    
   05        WZ-LINES-REPLACED       PIC 9(6).                    
   05        WZ-LINES-DELETED        PIC 9(6).                    
   05        WZ-COMMENTS-DELETED     PIC 9(6).                    
   05        WZ-CODE-REMOVED         PIC 9(6).                    
   05        WZ-SOURCE-PROGS         PIC 9(6).                    
   05        WZ-NEWPOP-PROGS         PIC 9(6).                    
   05        WZ-PROGS-FOUND          PIC 9(6).                    
   05        WZ-COMMENTS-DEL         PIC 9(6).                    
   05        WZ-SEQ-NO               PIC 9(6).                    
   05        WZ-SAVE-POP-RECORD.                                  
     10      WZ-SAVE-SEQ             PIC X(6).                    
     10      FILLER                  PIC X(5).                    
     10      WZ-SAVE-12-20.                                       
       15    WZ-SAVE-12-15           PIC X(4).                    
       15    FILLER                  PIC X(5).                    
     10      FILLER                  PIC X(60).                   
   05        WZ-PAGE-CT              PIC 9(6).                    
   05        WZ-LINE-CT              PIC 9(6).                    
   05        WZ-MODULE               PIC XX.                      
   05        WZ-LEVEL                PIC X.                       
   05        WZ-PRINT-HOLD           PIC X(132).                  
   05        WZ-X-CARD.                                           
     10      WZ-X-CHAR               PIC X                        
                                     OCCURS 60.                   
   05        WZ-WITHIN-DELETE-SERIES-FLAG  PIC X.                 
 01  WZ-VERSION-CARD.                                             
     10  FILLER                      PIC X(55) VALUE              
     "CCVS85  VERSION 4.2   01 OCT 1992 0032                 ".   
 01  WZ-VERSION-CONTROL REDEFINES WZ-VERSION-CARD.                
     10      FILLER                  PIC X(16).                   
     10      WZ-VERSION-NUM          PIC X(3).                    
     10      FILLER                  PIC X(3).                    
     10      WZ-VERSION-DATE         PIC X(11).                   
                                                                  
                                                                
 PROCEDURE DIVISION.                                              
*>==================                                               
*>                                                                 
 A10-MAIN SECTION.                                                
*>================                                                 
*>                                                                 
*>***************************************************************  
*>    THIS IS THE HIGHEST LEVEL CONTROL MODULE                  *  
*>                                                              *  
*>***************************************************************  
 A10-1-MAIN.                                                      
     PERFORM B10-INITIALISE.                                      
                                                                  
     PERFORM C10-PROCESS-MONITOR.                                 
                                                                  
     PERFORM D10-MERGE-UPDATE-CARDS.                              
                                                                  
     PERFORM E10-TERMINATE.                                       
                                                                  
 A10-EXIT.                                                        
     EXIT.                                                        
                                                                  
                                                               
 B10-INITIALISE SECTION.                                          
*>======================                                           
*>                                                                 
*>***************************************************************  
*> THIS SECTION INITIALIZES THE OPTION SWITCH AND X-CARD FIELDS *  
*> PRIOR TO READING IN CONTROL CARD FILE.                       *  
*>                                                              *  
*>                                                              *  
*>                                                              *  
*>                                                              *  
*>***************************************************************  
 B10-1-INIT-OPTION-SWITCHES.                                      
     MOVE    SPACES  TO WZ-MISCELLANEOUS.                         
     MOVE    SPACES  TO WF-PROGRAM-SELECTED-TABLE.                
     MOVE    SPACES  TO WG-MODULE-SELECTED-TABLE.                 
     MOVE    SPACES  TO WY-SWITCHES.                              
     MOVE    "A"     TO WY-OPT-SW-1.                              
     MOVE    "E"     TO WY-OPT-SW-2.                              
     MOVE    "H"     TO WY-OPT-SW-3.                              
     MOVE    "L"     TO WY-OPT-SW-4.                              
     MOVE    "Y"     TO WY-OPT-SW-7.                              
     MOVE    "T"     TO WY-OPT-SW-11.                             
                                                                  
 B10-2-INIT-X-CARDS.                                              
     MOVE    ZERO TO SUB1.                                        
     MOVE    ZERO TO SUB6.                                        
     MOVE    ZERO TO SUB7.                                        
     MOVE    1    TO SUB5.                                        
     PERFORM B20-INIT-X-CARDS 200 TIMES.                          
     MOVE   "    OMITTED" TO WX-X-CARD (84).                      
     MOVE    ZERO TO WZ-LINES-COBOL.                              
     MOVE    ZERO TO WZ-LINES-INSERTED.                           
     MOVE    ZERO TO WZ-LINES-REPLACED.                           
     MOVE    ZERO TO WZ-LINES-DELETED.                            
     MOVE    ZERO TO WZ-COMMENTS-DELETED.                         
     MOVE    ZERO TO WZ-CODE-REMOVED.                             
     MOVE    ZERO TO WZ-SOURCE-PROGS.                             
     MOVE    ZERO TO WZ-NEWPOP-PROGS.                             
     MOVE    ZERO TO WZ-PROGS-FOUND.                              
     MOVE    ZERO TO WZ-COMMENTS-DEL.                             
     MOVE    ZERO TO WZ-SEQ-NO.                                   
     MOVE    ZERO TO WZ-PAGE-CT.                                  
     MOVE    ZERO TO WZ-LINE-CT.                                  
     ACCEPT WA-DATE FROM DATE.                                    
 B10-EXIT.                                                        
     EXIT.                                                        
                                                                  
                                                                 