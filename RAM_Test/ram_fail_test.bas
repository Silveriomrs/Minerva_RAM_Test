100 REMark 1990 Dominic Brown
110 INK#1,7:PAPER#1,2
120 INK#0,4:PAPER#0,0
130 WINDOW#1,480,200,32,16
140 WINDOW#0,480,40,32,216
150 BORDER#1;1,0,7
160 FOR i=0 TO 1:CSIZE#i,1,0
170 DIM x(16),y(16),ram_chip(16)
180 DIM faulty_address$(20),go_again$(2)
190 RESTORE
200 true = 1
210 false = 0
220 FOR i = 1 TO 16:READ x(i),y(i)
230 REPeat MAIN_LOOP
240   PAPER 0
250   CLS:CLS#0
260   REPeat get_address_loop
270     INPUT#0,'Enter Address reported as faulty ';faulty_address$
280     IF faulty_address$<>''
290       faulty_address = DEC(faulty_address$)
300       IF faulty_address <> 1E9: EXIT get_address_loop
310       PR 2,'Invalid Character used in Hex try again!!!'
320     END IF
330   END REPeat get_address_loop
340   IF faulty_address > 262144
350     PR 7,'Faulty ram is in expansion memory!!!'
360   ELSE
370     d1$=get_data$("Enter first data value  >")
380     d2$=get_data$("Enter second data value >")
385     failmap=0
395     FOR i=1 TO 7 STEP 2:failmap=failmap||xorbyte(d1$(i TO i+1),d2$(i TO i+1))
450     hibank=8*(faulty_address>=196608)
455 PRINT #0;BIN$(failmap,8)
460     FOR i=1 TO 16:ram_chip(i) = false
470     mask%=128
480     FOR i=8 TO 1 STEP -1:IF failmap&&mask% THEN ram_chip(i+hibank) = true:bad_chip=i+hibank:END IF :mask%=mask%/2
490     FOR ic = 1 TO 16
500       IF ram_chip(ic) = true
510         block_colour = 2
520       ELSE
530         block_colour = 7
540       END IF
550       BLOCK 25,45,x(ic),y(ic),block_colour
560       CURSOR x(ic)+5,y(ic)+10
570       PRINT 'IC'
580       CURSOR x(ic)+5,y(ic)+20
590       PRINT ic
600     END FOR ic
610     PR 7,'The error has occured in IC'&bad_chip
620   END IF
630   PRINT#0,'Press Esc to Finish, any other key to continue '
640   go_again$=INKEY$(#0,-1)
650   IF go_again$ = CHR$(27): EXIT MAIN_LOOP
660 END REPeat MAIN_LOOP
670 STOP
680 :
690 DEFine FuNction get_data$(x$)
695   LOCal ans$
700   REPeat l
710     INPUT #0;(x$);ans$
740  REMark   IF res==1E9 THEN PRINT #0;'Invalid hex digit!':NEXT l
750     EXIT l
760   END REPeat l
770   ans$=FILL$('0',8)&ans$
780   ans$=ans$(LEN(ans$)-7 TO)
860   RETurn ans$
870 END DEFine
872 :
875 DEFine FuNction xorbyte(x$,y$)
877   RETurn DEC(x$)^^DEC(y$)
878 END DEFine
880 :
890 DEFine FuNction DEC(A$)
900   LOCal S,T,Q,valid_chr
910   LOCal valid_chr$(22)
920   T=0
930   valid_chr$ = '0123456789abcdef'
940   FOR Q=1 TO LEN(A$)
950     valid_chr=A$(Q) INSTR valid_chr$
960     IF valid_chr = false:EXIT Q
1000     T=T*16+valid_chr-1
1010   END FOR Q
1040   IF valid_chr = false:RETurn 1E9
1050   RETurn T
1060 END DEFine DEC
1070 :
1080 DEFine PROCedure PR(ink_colour,statement$)
1090   INK#0,ink_colour
1100   PRINT#0,statement$
1110   INK#0,4
1120 END DEFine PR
1130 DATA 90,40
1140 DATA 130,40
1150 DATA 170,40
1160 DATA 210,40
1170 DATA 250,40
1180 DATA 290,40
1190 DATA 330,40
1200 DATA 410,110
1210 DATA 90,110
1220 DATA 130,110
1230 DATA 170,110
1240 DATA 210,110
1250 DATA 250,110
1260 DATA 290,110
1270 DATA 330,110
1280 DATA 370,110

