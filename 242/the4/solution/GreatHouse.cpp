#include "GreatHouse.h"

GreatHouse::GreatHouse()
{
	name = "noName";
	duellist = NULL;
	setSupporterCount(50);
	houseType = 1;
}

GreatHouse::GreatHouse(std::string houseName)
{
	name = houseName;
	duellist = NULL;
	setSupporterCount(50);
	houseType = 1;
}

void GreatHouse::assignCharacter(Character * const character, Religion religion)
{
	duellist = character;
	duellist->setReligion(religion);
}

void GreatHouse::dismissCharacter()
{
	duellist->setReligion(Religion(RELIGION_NONE));
	duellist = NULL;
}

std::string GreatHouse::getName() const
{
	return name;
}

Character * GreatHouse::getDuellist() const
{
	return duellist;
}

int GreatHouse::getSupporterCount() const
{
	return supporterCount;
}

void GreatHouse::setSupporterCount(int supporterCount)
{
	if (supporterCount > 100) supporterCount = 100;
	if (supporterCount < 0) supporterCount = 0;
	this->supporterCount = supporterCount;
}

void GreatHouse::increaseSupportCount()
{
	supporterCount += 5;
	if (supporterCount > 100) supporterCount = 100;
	if (supporterCount < 0) supporterCount = 0;
}

void GreatHouse::decreaseSupportCount()
{
	supporterCount -= 5;
	if (supporterCount > 100) supporterCount = 100;
	if (supporterCount < 0) supporterCount = 0;
}

void GreatHouse::disappointedMatch(bool hasWon)
{
	if (hasWon)
	{
		supporterCount += 4;
		if (supporterCount > 100) supporterCount = 100;
		if (supporterCount < 0) supporterCount = 0;
	}
	if (!hasWon)
	{
		supporterCount -= 8;
		if (supporterCount > 100) supporterCount = 100;
		if (supporterCount < 0) supporterCount = 0;
	}
}

void GreatHouse::boredMatch()
{
	supporterCount -= 6;
	if (supporterCount > 100) supporterCount = 100;
	if (supporterCount < 0) supporterCount = 0;
}

int GreatHouse::getHouseType()
{
	return houseType;
}
