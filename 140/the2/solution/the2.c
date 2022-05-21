#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct queryNode{
	char query[6];
	struct queryNode *next;
	struct queryNode *prev;
};

struct countNode{
	int count;
	struct countNode *next;
	struct countNode *prev;
	struct queryNode *ext;
};

struct cacheNode{
	char query[6];
	struct queryNode *p2q;
};

typedef struct queryNode queryNode;
typedef struct countNode countNode;
typedef struct cacheNode cacheNode;

int searchCache(char query[]);
void initCache();
void addCache(char query[]);
countNode* searchCountNode(int value);
countNode* addCountNode(int value);
void printCache();
void printCountList();
void printCNode(countNode *p);
void addQueryNode(countNode* search,char query[], int lastpos);
void sortQuery(queryNode *head);
queryNode* searchQueryNode(char query[6],countNode **search);
void delQueryNode(queryNode *tbd,countNode *search);
void delCountNode(countNode *tbd);
void arrangeCache(char tbdquery[6],char query[6]);

int i=0,lastpos=0;
int count=0;
countNode *cp;
cacheNode cache[10];
FILE *inp,*out;

int main(){
	
	int inpCount,searchCount,j;
	countNode* search;
	queryNode* arama;
	char query[6],c,tbdquery[6];
	
	inp=fopen("input.txt","r+");
	out=fopen("output.txt","w+");
	
	initCache();
	cp=malloc(sizeof(countNode));
	cp->next=cp->prev=NULL;cp->count=-1;
	cp->ext=NULL;
	
	fscanf(inp,"%d",&inpCount);
	for(j=0;j<inpCount;j++){
		fscanf(inp,"%s", query);
		fscanf(inp,"%d", &searchCount);
		if(searchCache(query)<0){
			lastpos=i;
			addCache(query);
			search=searchCountNode(searchCount);
			if(search==NULL){
				search=addCountNode(searchCount);
				addQueryNode(search, query, lastpos);
			}
			else{
				addQueryNode(search, query, lastpos);
			}
			
		}
	}
	while((c=fscanf(inp,"%s",query))!=EOF){
		j=searchCache(query);
		if(j!=-1){
			lastpos=j;
			arama=searchQueryNode(query,&search);

			delQueryNode(arama,search);
			
			search=searchCountNode(count+1);
			if(search==NULL){
				search=addCountNode(count+1);
				addQueryNode(search, query, lastpos);
			}
			else{
				addQueryNode(search, query, lastpos);
			}
			/*printCountList();
			fprintf(out,"\n");*/
		}
		else{
			if(i<10){
				lastpos=i;
				addCache(query);
				search=searchCountNode(1);
				if(search==NULL){
					search=addCountNode(1);
					addQueryNode(search, query, lastpos);
				}
				else{
					addQueryNode(search, query, lastpos);
				}
			}
			else{
				arrangeCache(tbdquery,query);
			}
			/*printCache();
			fprintf(out,"\n");
			printCountList();
			fprintf(out,"\n");*/
		}
	}
	/*printCache();
	fprintf(out,"\n");*/
	printCountList();
	fprintf(out,"\n");
	fclose(inp);
	fclose(out);
	return 0;
}

void arrangeCache(char tbdquery[6],char query[6]){
	
	countNode *search;
	countNode *p=cp->next;
	queryNode *qp=p->ext;
	strcpy(tbdquery,qp->query);
	lastpos=searchCache(tbdquery);
	strcpy(cache[lastpos].query,"_");
	cache[lastpos].p2q=NULL;
	delQueryNode(qp,p);
	strcpy(cache[lastpos].query,query);
	search=searchCountNode(1);
	if(search==NULL){
		search=addCountNode(1);
		addQueryNode(search,query,lastpos);
	}
	else{
		addQueryNode(search,query,lastpos);
	}
}

queryNode* searchQueryNode(char query[6],countNode **search){
	countNode *p=cp;
	queryNode *q;
	while(p){
		q=p->ext;
		while(q){
			if(!strcmp(query,q->query)){
				count=p->count;
				*search=p;
				return q;
			}
			q=q->next;
		}
		p=p->next;
	}
	return NULL;
}

void delQueryNode(queryNode *tbd,countNode *search){
	queryNode *p=tbd;
	if(p->next!=NULL&&p->prev!=NULL){
		tbd->prev->next=tbd->next;
		tbd->next->prev=tbd->prev;
		free(tbd);
	}

	else if(p->next==NULL){
		if(p->prev) p->prev->next=NULL;
		else search->ext=NULL;
		free(tbd);
	}
	
	else if((p->prev)==NULL){
		search->ext=p->next;
		p->next->prev=NULL;
		free(tbd);
	}
	if(search->ext==NULL) delCountNode(search);

}

void delCountNode(countNode *tbd){
	if(tbd->next!=NULL) tbd->next->prev=tbd->prev;
	tbd->prev->next=tbd->next;
	free(tbd);
}

int searchCache(char query[]){
	int i;
	for(i=0;i<10;i++){
		if(!(strcmp(cache[i].query,query))){
			return i;
		}
	}
	return -1;
}

void addCache(char query[]){
	strcpy(cache[i].query,query);
	i++;
}

countNode* searchCountNode(int value){
	countNode *p=cp;
	while(p!=NULL){
		if(p->count==value){
			return p;
		}
		p=p->next;
	}
	return NULL;
}

countNode* addCountNode(int value){
	countNode *p=cp,*newp,*temp;
	while((p->next!=NULL)&&(value>p->next->count)){
		p=p->next;
	}
	newp=malloc(sizeof(countNode));
	temp=p->next; p->next=newp;
	newp->count=value; newp->next=temp; newp->prev=p; newp->ext=NULL;
	if(temp!=NULL) temp->prev=newp;
	return newp;
}

void addQueryNode(countNode* search,char query[], int lastpos){
	queryNode *newqp,*p,*temp;
	p=search->ext;
	if(p==NULL){
		newqp=malloc(sizeof(queryNode));
		strcpy(newqp->query,query); newqp->next=NULL; newqp->prev=NULL;
		search->ext=newqp;
		cache[lastpos].p2q=newqp;
	}
	else{
		
		while(p->next){
			p=p->next;
		}
		newqp=malloc(sizeof(queryNode));
		temp=p->next;
		p->next=newqp;
		strcpy(newqp->query,query); 
		newqp->next=temp; 
		newqp->prev=p;
		cache[lastpos].p2q=newqp;
	}
	sortQuery(search->ext);

	
}

void sortQuery(queryNode *head){
	queryNode *p=head;
	char temp[6];
	int flag=1;
	
	while(flag){
		flag=0;
		while(p->next){
			if(strcmp(p->query,p->next->query)>0)
			{
				strcpy(temp,p->query);
				strcpy(p->query,p->next->query);
				strcpy(p->next->query,temp);
				flag=1;
			}
			p=p->next;
		}
		p=head;
	}
	
}

void initCache(){
	int i;
	for(i=0;i<10;i++){
		strcpy(cache[i].query,"_");
		cache[i].p2q=NULL;
	}
}

void printCache(){
	int i;
	for(i=0;i<10;i++){
		fprintf(out,"  %s ",cache[i].query);
	}
}

void printCountList(){
	countNode *p=cp->next;
	queryNode *q;
	while(p){
		q=p->ext;
		fprintf(out,"%d",p->count);
		while(q){
			fprintf(out," %s",q->query);
			q=q->next;
		}
		p=p->next;
		fprintf(out,"\n");
	}
}

void printCNode(countNode *p){
	queryNode *q;
	fprintf(out,"|%d|-> ", p->count);
	q=p->ext;
	while(q){
		fprintf(out,"%s -> ",q->query);
		q=q->next;
	}
}
