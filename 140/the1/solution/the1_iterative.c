#include <stdio.h>

char board[1000][1000],tbr,rep;
int rows,cols,currentNode=-1;
int markedNode[1000*1000][2];

void markNode(int y,int x);
void checkMarkedNodes();

int main()
{
	int i,j,x,y;
	char temp;
	scanf("%d %d",&rows,&cols);
	scanf("%c",&temp);
	for(i=0;i<rows;i++)
	{
		for(j=0;j<cols;j++)
		{
			scanf("%c",&board[i][j]);
		}
		scanf("%c",&temp);
	}
	
	scanf("%d %d %c",&y,&x,&rep);
	
	tbr=board[y][x];
	
	board[y][x]=rep;
	
	markNode(y,x);
	
	checkMarkedNodes();
	
	for(i=0;i<rows;i++){
		for(j=0;j<cols;j++){
			printf("%c",board[i][j]);
		}
		printf("\n");
	}
	
	return 0;
}

void markNode(int y,int x)
{
	currentNode++;
	markedNode[currentNode][0]=y;
	markedNode[currentNode][1]=x;	
}

void checkMarkedNodes()
{
	int originy,originx,starty,startx,endy,endx,i,j;
	while(currentNode>=0)
	{
		originy=markedNode[currentNode][0];
		originx=markedNode[currentNode][1];
		
		starty=originy-1;
		startx=originx-1;
		endy=originy+1;
		endx=originx+1;
		if(starty<0){starty=0;}
		if(startx<0){startx=0;}
		if(endy>=rows){endy=rows-1;}
		if(endx>=cols){endx=cols-1;}
		currentNode--;
		for(i=starty;i<=endy;i++)
		{
			for(j=startx;j<=endx;j++)
			{
				if(board[i][j]==tbr)
				{
					board[i][j]=rep;
					markNode(i,j);
				}
			}
		}
	}
}
