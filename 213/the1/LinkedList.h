#ifndef _LINKED_LIST_H_
#define _LINKED_LIST_H_

#include <cstdlib>
#include "LinkedNode.h"

template<class T>
class LinkedList {
private:
	LinkedNode<T> *head;
	LinkedNode<T> *tail;
public:
    /* Default constructor. Initialize member variables. */
	LinkedList();

	/* Default destructor. Free all the memory used. */
	~LinkedList();

	/* Returns head of the linked list. */
	LinkedNode<T>* getHead() const;

	/* Returns tail of the linked list. */
	LinkedNode<T>* getTail() const;

	/* Returns idx-th node of the linked list under 0-based numbering.
	 */
	LinkedNode<T>* getNodeAt(int idx) const;

	/* Inserts a new node containing _data_ at the beginning of the linked list.
	 */
	void insertFirst(T &data);

	/* Inserts a new node containing _data_ after _node_.
	 */
	void insertNode(LinkedNode<T>* node, T &data);

	/* Deletes _node_ from the linked list
	 */
	void deleteNode(LinkedNode<T>* node);
};

template<class T>
LinkedList<T>::LinkedList()
{
	head=NULL;
	tail=NULL;
}

template<class T>
LinkedList<T>::~LinkedList()
{
    LinkedNode<T> *tbd;
    while(head)
    {
        tbd=head;
        head=head->getNext();
        delete tbd;
    }
}
				/*Getters*/
template<class T>
LinkedNode<T>* LinkedList<T>::getHead() const {return head;}

template<class T>
LinkedNode<T>* LinkedList<T>::getTail() const {return tail;}

template<class T>
LinkedNode<T>* LinkedList<T>::getNodeAt(int idx) const
{
	int i=0;
	LinkedNode<T> *current=head;
	while(i<idx)
	{
		current=current->getNext();
		i++;
	}
	return current;
}


template<class T>
void LinkedList<T>::insertFirst(T &data)
{
	LinkedNode<T>* newNode= new LinkedNode<T>;
	if(!head)
	{
		head=newNode;
		tail=newNode;
		newNode->setData(data);
		newNode->setNext(NULL);
		newNode->setPrev(NULL);
	}
	else
	{
        newNode->setData(data);
        newNode->setNext(head);
        newNode->setPrev(NULL);
        head->setPrev(newNode);
        head=newNode;
	}
}

template<class T>
void LinkedList<T>::insertNode(LinkedNode<T>* node, T &data)
{
    LinkedNode<T>* newNode = new LinkedNode<T>;
    newNode->setData(data);
    LinkedNode<T>* nodeNext=node->getNext();
    newNode->setNext(nodeNext);
    newNode->setPrev(node);
    node->setNext(newNode);
    if(nodeNext) nodeNext->setPrev(newNode);
    if(node==tail) tail=newNode;

}

template<class T>
void LinkedList<T>::deleteNode(LinkedNode<T>* node)
{
    if(node==tail)
    {
        node->getPrev()->setNext(NULL);
        tail=node->getPrev();
        delete node;
    }
    else if(node==head)
    {
        head=node->getNext();
        head->setPrev(NULL);
        delete node;
    }
    else
    {
        node->getPrev()->setNext(node->getNext());
        node->getNext()->setPrev(node->getPrev());
        delete node;
    }
}


#endif
