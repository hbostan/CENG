#include <stdio.h>


void replace(int x,int y,char tbr,char rep);

char board[1000][1000];
int rows,cols;

int main() {
	int i,j,x,y;
	char rep,tbr;
	scanf("%d %d",&rows,&cols);
	
	for(i=0;i<rows;i++){
		for(j=0;j<cols;j++){
			scanf("%c",&board[i][j]);
			if(board[i][j]=='\n'){
				scanf("%c",&board[i][j]);
			}
		}
	}
	
	scanf("%d %d %c",&y,&x,&rep);
	
	tbr=board[y][x];
	
	replace(y,x,tbr,rep);
	
	for(i=0;i<rows;i++){
		for(j=0;j<cols;j++){
			printf("%c",board[i][j]);
		}
		printf("\n");
	}
	
	
	return 0;
}

void replace(int y,int x,char tbr,char rep){
	if((y<0)||(y+1>rows)||(x<0)||(x+1>cols)){
		return;
	}
	
	if(board[y][x]==tbr){
		board[y][x]=rep;
		replace(y-1,x-1,tbr,rep);
		replace(y-1,x+1,tbr,rep);
		replace(y+1,x-1,tbr,rep);
		replace(y+1,x+1,tbr,rep);
		replace(y-1,x,tbr,rep);
		replace(y+1,x,tbr,rep);
		replace(y,x-1,tbr,rep);
		replace(y,x+1,tbr,rep);
	}
	return;
}
