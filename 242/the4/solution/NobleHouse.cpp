#include "NobleHouse.h"

NobleHouse::NobleHouse()
{
	name = "noName";
	duellist = NULL;
	setSupporterCount(30);
	houseType = 2;
}

NobleHouse::NobleHouse(std::string houseName)
{
	name = houseName;
	duellist = NULL;
	setSupporterCount(30);
	houseType = 2;
}

void NobleHouse::assignCharacter(Character * const character, Religion religion)
{
	duellist = character;
	duellist->setReligion(religion);
}

void NobleHouse::dismissCharacter()
{
	duellist->setReligion(Religion(RELIGION_NONE));
	duellist = NULL;
}

std::string NobleHouse::getName() const
{
	return name;
}

Character * NobleHouse::getDuellist() const
{
	return duellist;
}

int NobleHouse::getSupporterCount() const
{
	return supporterCount;
}

void NobleHouse::setSupporterCount(int supporterCount)
{
	if (supporterCount > 60) supporterCount = 60;
	if (supporterCount < 0) supporterCount = 0;
	this->supporterCount = supporterCount;
}

void NobleHouse::increaseSupportCount()
{
	supporterCount += 3;
	if (supporterCount > 60) supporterCount = 60;
	if (supporterCount < 0) supporterCount = 0;
}

void NobleHouse::decreaseSupportCount()
{
	supporterCount -= 3;
	if (supporterCount > 60) supporterCount = 60;
	if (supporterCount < 0) supporterCount = 0;
}

void NobleHouse::fight()
{
	supporterCount -= (supporterCount / 5);
	if (supporterCount > 60) supporterCount = 60;
	if (supporterCount < 0) supporterCount = 0;
}

int NobleHouse::getHouseType()
{
	return houseType;
}
