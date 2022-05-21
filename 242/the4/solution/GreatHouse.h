#ifndef GREAT_HOUSE_H
#define GREAT_HOUSE_H

#include "House.h"

// TODO: Define Great House Class

class GreatHouse :public House
{
public:
	GreatHouse();
	GreatHouse(std::string houseName);
	virtual ~GreatHouse() {}

	virtual void assignCharacter(Character* const character, Religion religion);
	virtual void dismissCharacter();

	virtual std::string getName() const;
	virtual Character* getDuellist() const;

	virtual int getSupporterCount() const;
	virtual void setSupporterCount(int supporterCount);

	virtual void increaseSupportCount();
	virtual void decreaseSupportCount();

	virtual void disappointedMatch(bool hasWon);
	virtual void boredMatch();

	virtual int getHouseType();
};

#endif // GREAT_HOUSE_H