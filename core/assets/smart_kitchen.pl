                   %  THE SMART KITCHEN

:- dynamic contains/3.
:- dynamic door_open/2.
:- dynamic door_closed/2.
:- dynamic in_hand/2.
:- dynamic unterschrank/1.

% Primitive control actions

primitive_action(abstellen(O,X)). 	% Abstellen von Gegenstand O auf der Arbeitsplatte X
primitive_action(herausnehmen(O,X)). 	% Entnehmen von Gegenstand O aus Schrank X
primitive_action(einstellen(O,X)). 	% Hineinstellen von Gegenstand O in Schrank X
primitive_action(oeffnen(X)).       		% Öffnen der Schranktüre X
primitive_action(schliessen(X)).       		% Schließen der Schranktüre X

% Definitions of Complex Control Actions

proc(einraeumen(O,X), oeffnen(O) : reinlegen(O,X) : schliessen(O)).
proc(ausraeumen(O,X), oeffnen(O) : herausnehmen(O,X) : schliessen(O)).
proc(putSchrank(X), ?(eingelagert(X)) # einraeumen(X)).
proc(takeSchrank(X), ?(eingelagert(X)) # ausraeumen(X)).

% Preconditions for Primitive Actions.

schrank(X) :- oberschrank(X).
schrank(X) :- unterschrank(X).

zutat(X) :- salz(X).
zutat(X) :- nudeln(X).

poss(herausnehmen(O,X),S) :- door_open(X,S), contains(X,O,S), schrank(X).
poss(einstellen(O,X),S) :- door_open(X,S), \+ contains(X,O,S), schrank(X).
poss(abstellen(O,X),S) :- in_hand(O,S), arbeitsplatte(X).
poss(oeffnen(O),S) :- door_closed(O,S).
poss(schliessen(O),S) :- door_open(O,S).

% Successor State Axioms for Primitive Fluents.

contains(X,O,do(A,S)) :-
	A = einstellen(O,X);
	(\+ A = einstellen(O,X), contains(X,O,S)).

in_hand(O,do(A,S)) :-
    A = herausnehmen(O,_);
    \+ A = herausnehmen(O,_), in_hand(O,S).

door_open(O,do(A,S)) :-
	A = oeffnen(O);
	\+ A = oeffnen(O), door_open(O,S).
	
door_closed(O,do(A,S)) :-
	A = schliessen(O);
	\+ A = schliessen(O), door_closed(O,S).

% Initial situation

unterschrank(o_12).
contains(o1,o38,s0).

% Restore suppressed situation arguments.

restoreSitArg(in_hand(O,X),S,in_hand(O,X,S)).
restoreSitArg(contains(O,X),S,contains(O,X,S)).
restoreSitArg(door_open(O),S,door_open(O,S)).
restoreSitArg(door_closed(O),S,door_closed(O,S)).
