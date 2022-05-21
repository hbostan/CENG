#include "wordnet.hpp"
#include <fstream>
#include <queue>

void WordNet::BuildWordNet(string filename)
{
	ifstream file;
	file.open(filename.c_str());
	string root, child, parent,line,temp;
	getline(file, root);
	addChild(root, root);
	while (!file.eof())
	{
		getline(file, child,' ');
		getline(file, temp, ' ');
		getline(file, parent, '\n');
		if(child.compare("") && parent.compare("")) addChild(child, parent);

	}

}

void WordNet::HandleTask(string command)
{
	string comm, arg1, arg2;
	comm= command.substr(0, command.find(' '));
	command.erase(0, comm.size()+1);
	if (command.size() > 0)
	{
		arg1 = command.substr(0, command.find(' '));
		command.erase(0, arg1.size() + 1);
	}
	if (command.size() > 0)
	{
		arg2 = command.substr(0, command.find(' '));
	}

	
	if (!comm.compare("PrintWordNet"))
	{
		print(root, 0, true);
	}
	if (!comm.compare("PrintSubclasses"))
	{
		Tree* node;
		node = find(arg1, root);
		cout << node->data << endl;
		printSub(node->firstChild);
	}
	if (!comm.compare("PrintSuperclasses"))
	{
		Tree* node;
		node = find(arg1, root);
		printSuper(node);
		cout << endl;
	}
	if (!comm.compare("PrintIntermediateClasses"))
	{
		Tree* node, *target;
		node = find(arg1, root);
		target = find(arg2, root);
		printInter(node, target);
		cout << endl;
	}
}

Tree * WordNet::find(string& query, Tree* root)
{
	if (root == NULL) return NULL;
	if (query.compare(root->data) == 0) return root;

	Tree *temp;
	for (temp = root->firstChild;temp != NULL;temp = temp->nextSibling)
	{
		Tree* target = find(query, temp);
		if (target != NULL)
			return target;
	}
	return NULL;
}

void WordNet::addChild(string & child, string & parent)
{
	Tree *current = root;
	Tree *newNode = new Tree;
	newNode->data = child;
	current = find(parent, current);
	newNode->parent = current;
	if (current == NULL)
	{
		root = newNode; return;
	}

	if (current->firstChild)
	{
		current = current->firstChild;
		while (current->nextSibling) current = current->nextSibling;
		current->nextSibling = newNode;
	}
	else
	{
		current->firstChild = newNode;
	}
}

void WordNet::print(Tree * root,int depth,bool mode)
{
	if (root == NULL)	return;

	if(mode) for (int i = 0;i < depth;i++) cout << "  ";
	cout << root->data;
	cout << endl;
	Tree *fc;
	for (fc = root->firstChild;fc != NULL;fc = fc->nextSibling)
	{
		print(fc, depth + 1,mode);
	}
}

void WordNet::printSuper(Tree *node)
{
	while (node->parent)
	{
		cout << node->data << " < ";
		node = node->parent;
	}
	cout << node->data;
}

void WordNet::printInter(Tree *node, Tree* target)
{
	while (node != target)
	{
		cout << node->data << " < ";
		node = node->parent;
	}
	cout << node->data;
}

void WordNet::printSub(Tree* root)
{
	Tree * current = root;
	Tree *temp;
	while (current)
	{
		que.push(current);
		current = current->nextSibling;
	}
	if (que.empty()) return;
	temp = que.front();
	cout << temp->data << endl;
	que.pop();
	printSub(temp->firstChild);
}