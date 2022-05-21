#include "Kingdom.h"

Kingdom::Kingdom(string kingdomName, Religion kingdomReligion)
{
	name = kingdomName;
	religion = kingdomReligion;
	duelPoints = 0;
	totalDuelCount =0;
}

Kingdom::~Kingdom()
{}

void Kingdom::assignGreatHouse(House * const greatHouse)
{
	this->greatHouse = greatHouse;
}

void Kingdom::dismissGreatHouse()
{
	this->greatHouse = NULL;
}

void Kingdom::addNobleHouse(House * const nobleHouse)
{
	nobleHouses.push_back(nobleHouse);
}

void Kingdom::removeNobleHouse(House * const nobleHouse)
{
	vector<House *>::iterator it;
	for (it = nobleHouses.begin(); it != nobleHouses.end();it++)
	{
		if (*it == nobleHouse)
		{
			nobleHouses.erase(it);
			break;
		}
	}
}

string Kingdom::getName()
{
	return name;
}

Religion Kingdom::getReligion()
{
	return religion;
}

void Kingdom::setReligion(Religion religion)
{
	this->religion = religion;
	this->greatHouse->getDuellist()->setReligion(religion);
	for (int i = 0; i < 2;i++)
	{
		nobleHouses[i]->getDuellist()->setReligion(religion);
	}
}

House * Kingdom::getGreatHouse()
{
	return greatHouse;
}

vector<House*> Kingdom::getNobleHouses()
{
	return nobleHouses;
}

void Kingdom::increaseDuelPoints()
{
	duelPoints++;
}

void Kingdom::increaseTotalDuelCount()
{
	totalDuelCount++;
}

int Kingdom::getTotalDuelCount()
{
	return totalDuelCount;
}

int Kingdom::getDuelPoints()
{
	return duelPoints;
}
