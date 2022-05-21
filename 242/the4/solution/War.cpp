#include "War.h"

void War::startSimulation()
{
	int x = 1,result,i;
	int* results = new int[v.size()];
	vector<int> winners;
	cout << "War has started with " << v.size() << " kingdoms." << endl;
	for (int i = 1;i < (int)v.size();i++)
	{
		for (int j = 0;j < (int)(v.size()-i);j++,x++)
		{

			cout << "---------------" << endl <<"Duel " << x << ": " << v[j]->getName() << " vs " << v[j + i]->getName() << endl;
			result = duelOfKingdoms(v[j], v[j + i]);
			switch (result)
			{
			case 0: v[j]->increaseDuelPoints(); v[j + i]->increaseDuelPoints(); cout << endl << "A draw." << endl<<"---------------"<<endl; break;
			case 1: v[j]->increaseDuelPoints(); cout << endl << "The winner is: " << v[j]->getName() << " with currently " << v[j]->getDuelPoints() << " kingdom points." << endl<<"---------------"<<endl; break;
			case -1: v[j + i]->increaseDuelPoints(); cout<< endl << "The winner is: " << v[j+i]->getName() << " with currently " << v[j+i]->getDuelPoints() << " kingdom points."<< endl << "---------------"<< endl; break;
			}
			results[j] = v[j]->getDuelPoints();
			results[j+i] = v[j+i]->getDuelPoints();
		}
	}

	int max = -1,index;
	for (int i = 0; i < (int)v.size(); i++)
	{
		if (results[i] > max)
		{
			max = results[i];
			index = i;
		}
	}
	for (int i = 0; i < (int)v.size();i++)
	{
		if(results[i]==max) winners.push_back(i);
	}
	if(winners.size()==1)
	{
		cout << "The War is over. The new owner of King's Landing is " << v[index]->getName() << " with a total of " << v[index]->getDuelPoints() << " points." << endl << endl;
	}
	else
	{
		cout << "The War is over. The new owners of King's Landing are ";
		for(i= 0 ;i<(int)(winners.size()-1) ; i++)
		{
			cout << v[winners[i]]->getName() << ", ";
		}
		cout << "and " << v[winners[i]]->getName() << "."<< endl << endl;
	}

	//clean up
	delete[] results;
	for(int i = 0; i<(int)v.size();i++)
	{
		for(int j =0;j<3;j++)
		{
			delete ((v[i]->getNobleHouses())[j]->getDuellist());
			delete (v[i]->getNobleHouses())[j];
		}
		delete (v[i]->getGreatHouse()->getDuellist());
		delete (v[i]->getGreatHouse());
		delete v[i];
	}

}

int War::duelOfKingdoms(Kingdom * kingdom1, Kingdom * kingdom2)
{
	int result;
	int i,k1p,k2p;
	k1p = k2p = 0;
	for (i = 0;i < 3;i++)
	{
		for (int j = 0; j < 3;j++)
		{
			result = duelOfHouses((kingdom1->getNobleHouses())[j], (kingdom2->getNobleHouses())[(j + i) % 3]);
			switch (result)
			{
			case 1:k1p++; break;
			case 0: break;
			case -1:k2p++; break;
			}
		}
	}
	result = duelOfHouses(kingdom1->getGreatHouse(), kingdom2->getGreatHouse());
	switch (result)
	{
	case 1:k1p+=3; break;
	case 0: break;
	case -1:k2p+=3; break;
	}
	cout << "  " << kingdom1->getName() << " got "<< k1p <<" house points. " << kingdom2->getName() << " got "<< k2p <<" house points." << endl;
	if (k1p > k2p)
	{
		return 1;
	}
	else if (k2p > k1p)
	{
		return -1;
	}
	else
	{

		return 0;
	}

}

int War::duelOfHouses(House * house1, House * house2)
{
	cout << "  Duel between " << house1->getName() << "(" << house1->getSupporterCount() << ")" << " " << house1->getDuellist()->getAbbreviation() << " and "
		<< house2->getName() << "(" << house2->getSupporterCount() << ")" << " " << house2->getDuellist()->getAbbreviation() << " has begun." << endl;
	if ((house1->getHouseType() == 1) && (house1->getHouseType() == house2->getHouseType())) //GreatHouse
	{
		pair<int, int> result = duelOfCharacters(house1->getDuellist(), house2->getDuellist(), house1->getSupporterCount(), house2->getSupporterCount());
		if (result.first == 1)
		{
			if (result.second <= 2)
			{
				house1->disappointedMatch(true);
				house2->disappointedMatch(false);
			}
			if (result.second > 6)
			{
				house1->boredMatch();
				house2->boredMatch();
			}
			house1->increaseSupportCount();
			house2->decreaseSupportCount();
			return 1;
		}
		else if (result.first == 0)
		{
			return 0;
		}
		else //if (result.first == -1)
		{
			if (result.second <= 2)
			{
				house2->disappointedMatch(true);
				house1->disappointedMatch(false);
			}
			if (result.second > 6)
			{
				house2->boredMatch();
				house1->boredMatch();
			}
			house2->increaseSupportCount();
			house1->decreaseSupportCount();
			return -1;
		}
	}
	else //if ((house1->getHouseType() == 2) && (house1->getHouseType() == house2->getHouseType())) //NobleHouse
	{
		pair<int, int> result = duelOfCharacters(house1->getDuellist(), house2->getDuellist(), house1->getSupporterCount(), house2->getSupporterCount());
		if (result.first == 1)
		{
			if (house2->getSupporterCount() > house1->getSupporterCount())
			{
				house1->fight();
			}
			house1->increaseSupportCount();
			house2->decreaseSupportCount();
			return 1;
		}
		else if (result.first == 0)
		{
			return 0;
		}
		else //if (result.first == -1)
		{
			if (house1->getSupporterCount() > house2->getSupporterCount())
			{
				house2->fight();
			}
			house2->increaseSupportCount();
			house1->decreaseSupportCount();
			return -1;
		}
	}
}

pair<int, int> War::duelOfCharacters(Character * character1, Character * character2, int cheerBonus1, int cheerBonus2)
{
	int roundCount = 0;
	int hpb, hpa;
	character1->restoreOriginalValues();
	character2->restoreOriginalValues();
	character1->removeDebuffs();
	character2->removeDebuffs();

	character1->applyCheerBonus(cheerBonus1);
	character2->applyCheerBonus(cheerBonus2);
	while ((!(character1->isDead())) && (!(character2->isDead())))
	{
		roundCount++;
		hpb = character1->getHealth();
		character1->applyDOTs();
		hpa = character1->getHealth();
		if (hpb - hpa > 0) cout<<"    " << character1->getName() << "(" << hpb<< ") took " << hpb - hpa << " damage as DOT." << endl;

		hpb = character2->getHealth();
		character2->applyDOTs();
		hpa = character2->getHealth();
		if (hpb - hpa > 0) cout << "    " << character2->getName() << "(" << hpb << ") took " << hpb - hpa << " damage as DOT." << endl;

		hpb = character2->getHealth();
		character1->attackTo(character2);
		hpa = character2->getHealth();
		cout << "    " << character1->getName() << "(" << character1->getHealth() << ")" << " hit " << character2->getName() << "(" << hpb << ") " << hpb - hpa<< "."<< endl;

		hpb = character1->getHealth();
		character2->attackTo(character1);
		hpa = character1->getHealth();
		cout << "    " << character2->getName() << "(" << character2->getHealth() << ")" << " hit " << character1->getName() << "(" << hpb << ") " << hpb - hpa <<"."<< endl;
		cout << "    " << "****" << endl;
	}
	if (character1->isDead() && character2->isDead())
	{
		cout << "    " << "Both of them are dead. A draw." << endl;
		return make_pair(0, roundCount);
	}
	else if (character1->isDead() && !(character2->isDead()))
	{
		cout << "    " << character2->getName() << "(" << character2->getHealth() << ") has won." << endl;
		return make_pair(-1, roundCount);
	}
	else //if (!(character1->isDead()) && character2->isDead())
	{
		cout << "    " << character1->getName() << "(" << character1->getHealth() << ") has won." << endl;
		return make_pair(1, roundCount);
	}
}

void War::addKingdom(Kingdom * kingdom)
{
	v.push_back(kingdom);
}

void War::removeKingdom(Kingdom * kingdom)
{
	vector<Kingdom*>::iterator it;
	for (it = v.begin(); it != v.end(); it++)
	{
		if ((*it) == kingdom)
		{
			v.erase(it);
			break;
		}
	}
}

int War::kingdomCount()
{
	return v.size();
}
