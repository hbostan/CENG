#ifndef TREE_H_
#define TREE_H_

#include <string>
#include <vector>

class Tree {

public:
	Tree() :firstChild(NULL), nextSibling(NULL) {};
	std::string data;
	Tree *firstChild;
	Tree *parent;
	Tree* nextSibling;

};


#endif
