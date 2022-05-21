#ifndef _HASHTABLE_H_
#define _HASHTABLE_H_

#include <string>
#include <vector>
#include <iostream>

using namespace std;

template <class T>
struct Node
{
	std::string key;
	T value;
	bool isDeleted;
	bool isFull;
	int accessCount;
	Node():isFull(false),isDeleted(false),accessCount(0),key(""){}

};

template <class T>
class HashTable
{
public:
	HashTable();
	void addKey(std::string key,T value);
	void deleteKey(std::string key);
	Node<T>* findKey(std::string key);
	int getElementCount(){
		return elementCount;
	}
	int getTableSize()
	{
		return tableSize;
	}
	Node<T>* getTable()
	{
		return table;
	}
	
private:
	Node<T>* table;
	int elementCount;
	int tableSize;
	int Hash(std::string key, int tableSize);
	void reHash();
	int getNextPrime(int sayi);
	
};

template <class T>
HashTable<T>::HashTable()
{
	elementCount=0;
	table= new Node<T>[17];
	tableSize=17;
}

template <class T>
void HashTable<T>::addKey(std::string key, T value)
{
	if(((float)elementCount/(float)tableSize)>0.5) reHash();
	 
	int idx=Hash(key,tableSize);
	Node<T> newItem;
	newItem.key=key;
	newItem.value=value;
	newItem.isFull=true;
	newItem.isDeleted=false;
	newItem.accessCount=0;
	while(table[idx].isFull==true && table[idx].key!=key)
	{
		idx+=7;
		idx%=tableSize;
	}
	elementCount++;
	table[idx]=newItem;
	
}

template <class T>
void HashTable<T>::deleteKey(std::string key)
{
	int idx= Hash(key,tableSize);
	if(table[idx].key==key)
	{
		table[idx].isDeleted=true;
		return;
	}
	while(table[idx].isFull)
	{
		idx+=7;
		idx%tableSize;
		if(table[idx].key==key)
		{
			table[idx].isDeleted=true; 
			return;
		}
	}
}

template <class T>
Node<T>* HashTable<T>::findKey(std::string key)
{
	int idx=Hash(key,tableSize);
	if(table[idx].key==key){ return &table[idx];}
	while(table[idx].isFull )
	{
		idx+=7;
		idx%=tableSize;
		if(table[idx].key==key) return &table[idx];
	}
	
	return NULL;

}

template <class T>
int HashTable<T>::Hash(std::string key, int tableSize)
{
	unsigned int index=0;
	for(int i=0;i<key.length();i++)
	{
		index=(index << 5)-index+key[i];
	}
	index%=tableSize;
	return index;
}

template <class T>
void HashTable<T>::reHash()
{
	Node<T>* temp=table;
	int oldSize=tableSize;
	tableSize=getNextPrime(tableSize);
	table= new Node<T>[tableSize]();
	elementCount=0;
	for(int i=0;i<oldSize;i++)
	{
		if(temp[i].isFull && !temp[i].isDeleted)
		{
			addKey(temp[i].key,temp[i].value);
		}
	}
	delete []temp;

}

template <class T>
int HashTable<T>::getNextPrime(int sayi)
{
		int FactorCount = 0;

	for (int i = 2*sayi; i >= 2; --i)
	{
		for (int j = 2; j*j<i + 1; ++j)
		{
			if (i % j == 0)
			{
				++FactorCount;
				break;
			}
		}
		if (FactorCount == 0)
		{
			return i;
			break;
		}
		FactorCount = 0;
	}
	return 0;
}


#endif
