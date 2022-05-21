#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <stack>
#include <string>
#include <tuple>
using namespace std;

class StateNode {
public:
	int parent;
	vector<int> adjacent;
	int rank;
	StateNode()
	{
		parent;
		rank = 0;
	}
};

vector<StateNode*> nodes;
vector<int> finals;
vector<bool> isFinal(false);
vector<bool> hasFinal(false);

int findSet(int x)
{
	//cout << "findSet\n";
	if (x != nodes[x]->parent)
	{
		nodes[x]->parent = findSet(nodes[x]->parent);
	}
	return nodes[x]->parent;
}

void link(int x, int y)
{
	//cout << "link\n";
	if (nodes[x]->rank > nodes[y]->rank)
	{
		nodes[y]->parent = x;
	}
	else
	{
		nodes[x]->parent = y;
	}
	if (nodes[x]->rank == nodes[y]->rank)
	{
		nodes[y]->rank++;
	}
}

void unite(int x, int y)
{
	//cout << "unite\n";
	link(findSet(x), findSet(y));
}

int main(int argc, char* argv[])
{

	ifstream file1(argv[1]);
	ifstream file2(argv[2]);
	int size1, sym1, start1, size2, sym2, start2, num;
	string line;
	file1 >> size1 >> sym1 >> start1;
	file1 >> ws;
	getline(file1, line);
	istringstream ss(line);
	isFinal.resize(size1,false);
	while (ss >> num)
	{
		finals.push_back(num);
		isFinal[num] = true;
	}
	for (int j = 0;j < size1;j++)
	{
		nodes.push_back(new StateNode());
		nodes[j]->parent = j;
		for (int i = 0; i < sym1;i++)
		{
			file1 >> num;
			nodes[j]->adjacent.push_back(num);
		}
		file1 >> ws;
	}

	file2 >> size2 >> sym2 >> start2;
	file2 >> ws;
	getline(file2, line);
	istringstream ss1(line);
	isFinal.resize(size1 + size2,false);
	while (ss1 >> num)
	{
		finals.push_back(num + size1);
		isFinal[num + size1] = true;
	}
	for (int j = size1;j < size1 + size2;j++)
	{
		nodes.push_back(new StateNode());
		nodes[j]->parent = j;
		for (int i = 0; i < sym2;i++)
		{
			file2 >> num;
			nodes[j]->adjacent.push_back(num + size1);
		}
		file2 >> ws;
	}


	/*cout << nodes.size() << endl;
	for (int i = 0;i < nodes.size();i++)
	{
		for (int j = 0; j < sym1; j++)
		{
			cout << nodes[i]->adjacent[j] << ' ';
		}
		cout << endl;
	}

	for (int i = 0;i < finals.size();i++)
	{
		cout << finals[i] << ' ';
	}
	cout << endl;
	for (int i = 0;i < isFinal.size();i++)
		cout << isFinal[i];
	cout << endl;*/

	if (sym1 != sym2)
	{
		cout << "notequal\n";
		return 0;
	}
	//===================================================
	tuple<int, int> t = make_tuple(start1, start2+size1);
	stack<tuple<int, int> > s;
	s.push(t);
	while (!s.empty())
	{
		t = s.top(); s.pop();
		int q1, q2;
		q1 = get<0>(t);
		q2 = get<1>(t);
		for (int i = 0; i < sym1; i++)
		{
			int r1 = findSet(nodes[q1]->adjacent[i]);
			int r2 = findSet(nodes[q2]->adjacent[i]);
			//cout << endl << nodes[q1]->adjacent[i] << ' ' << nodes[q2]->adjacent[i]<< endl;
			//cout << endl << r1 << ' ' << r2 << endl;
			if(r1!=r2)
			{
				unite(r1, r2);
				s.push(make_tuple(r1, r2));
			}
		}
	}

	hasFinal.resize(size1 + size2, false);
	for (int i = 0;i < finals.size();i++)
	{
		hasFinal[findSet(finals[i])] = true;
	}

	for (int i = 0; i < nodes.size();i++)
	{
		if ((!isFinal[i]) && (hasFinal[findSet(i)]))
		{
			cout << "notequal\n";
			return 0;
		}
	}

	/*for (int i = 0; i < finals.size(); i++)
	{
		int rep = findSet(finals[i]);
		for (int j = 0; j < nodes.size(); j++)
		{
			int rep1 = findSet(j);
			if (rep1 == rep && !isFinal[j])
			{
				cout << "notequal\n";
				int t123;
				cin >> t123;
				return 0;
			}
		}
	}*/

	cout << "equal\n";
	return 0;
}
