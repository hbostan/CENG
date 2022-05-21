#ifndef WN_H_
#define WN_H_

#include <iostream>
#include <string>
#include <vector>
#include "tree.hpp"
#include <queue>

using namespace std;

class WordNet {

public:
	WordNet() :root(NULL) {}
	void BuildWordNet(string);
	void HandleTask(string);
	Tree* getRoot() { return root; }

private:
	Tree *root;
	Tree* find(string& query,Tree* root);
	void addChild(string& child, string& parent);
	void print(Tree* root, int depth, bool mode);
	void printSuper(Tree *root);
	void printInter(Tree *node, Tree* target);
	void printSub(Tree* root);
	queue <Tree*> que;
	

};

#endif
