:- module(hw5, [complete_path/5]).
complete_path(_,position(PosX,PosY),position(FinX,FinY),[],[]) :- PosX = FinX , PosY=FinY,!.


complete_path(N,position(PosX,PosY),position(FinX,FinY),[north|RemainingMoves],[north|Rem]) :- Temp is PosY-1, Temp > -1 ,!, complete_path(N,position(PosX,Temp),position(FinX,FinY),RemainingMoves,Rem).
complete_path(N,position(PosX,PosY),position(FinX,FinY),[north|RemainingMoves],Rem) :- Temp is PosY-1, Temp < 0 ,!, complete_path(N,position(PosX,PosY),position(FinX,FinY),RemainingMoves,Rem).

complete_path(N,position(PosX,PosY),position(FinX,FinY),[east|RemainingMoves],[east|Rem]) :-Temp is PosX+1, Temp < N ,!,complete_path(N,position(Temp,PosY),position(FinX,FinY),RemainingMoves,Rem).
complete_path(N,position(PosX,PosY),position(FinX,FinY),[east|RemainingMoves],Rem) :-Temp is PosX+1, Temp >= N ,!,complete_path(N,position(PosX,PosY),position(FinX,FinY),RemainingMoves,Rem).

complete_path(N,position(PosX,PosY),position(FinX,FinY),[south|RemainingMoves],[south|Rem]) :-Temp is PosY+1, Temp < N ,!, complete_path(N,position(PosX,Temp),position(FinX,FinY),RemainingMoves,Rem).
complete_path(N,position(PosX,PosY),position(FinX,FinY),[south|RemainingMoves],Rem) :-Temp is PosY+1, Temp >= N ,!, complete_path(N,position(PosX,PosY),position(FinX,FinY),RemainingMoves,Rem).

complete_path(N,position(PosX,PosY),position(FinX,FinY),[west|RemainingMoves],[west|Rem]) :- Temp is PosX-1, Temp > -1 ,!, complete_path(N,position(Temp,PosY),position(FinX,FinY),RemainingMoves,Rem).
complete_path(N,position(PosX,PosY),position(FinX,FinY),[west|RemainingMoves],Rem) :- Temp is PosX-1, Temp < 0 ,!, complete_path(N,position(PosX,PosY),position(FinX,FinY),RemainingMoves,Rem).




complete_path(N,position(PosX,PosY),position(FinX,FinY),[unknown|RemainingMoves],[north|Rem]) :-nonvar(Rem),complete_path(N,position(PosX,PosY),position(FinX,FinY),[north|RemainingMoves],[north|Rem]),!.
complete_path(N,position(PosX,PosY),position(FinX,FinY),[unknown|RemainingMoves],[east|Rem]) :- nonvar(Rem),complete_path(N,position(PosX,PosY),position(FinX,FinY),[east|RemainingMoves],[east|Rem]),!.
complete_path(N,position(PosX,PosY),position(FinX,FinY),[unknown|RemainingMoves],[south|Rem]) :-nonvar(Rem),complete_path(N,position(PosX,PosY),position(FinX,FinY),[south|RemainingMoves],[south|Rem]),!.
complete_path(N,position(PosX,PosY),position(FinX,FinY),[unknown|RemainingMoves],[west|Rem]) :- nonvar(Rem),complete_path(N,position(PosX,PosY),position(FinX,FinY),[west|RemainingMoves],[west|Rem]),!.

complete_path(N,position(PosX,PosY),position(FinX,FinY),[unknown|RemainingMoves],[north|Rem]) :-complete_path(N,position(PosX,PosY),position(FinX,FinY),[north|RemainingMoves],[north|Rem]).
complete_path(N,position(PosX,PosY),position(FinX,FinY),[unknown|RemainingMoves],[east|Rem]) :- complete_path(N,position(PosX,PosY),position(FinX,FinY),[east|RemainingMoves],[east|Rem]).
complete_path(N,position(PosX,PosY),position(FinX,FinY),[unknown|RemainingMoves],[south|Rem]) :-complete_path(N,position(PosX,PosY),position(FinX,FinY),[south|RemainingMoves],[south|Rem]).
complete_path(N,position(PosX,PosY),position(FinX,FinY),[unknown|RemainingMoves],[west|Rem]) :- complete_path(N,position(PosX,PosY),position(FinX,FinY),[west|RemainingMoves],[west|Rem]).



