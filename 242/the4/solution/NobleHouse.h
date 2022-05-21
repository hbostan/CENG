#ifndef NOBLE_HOUSE_H
#define NOBLE_HOUSE_H

#include "House.h"

// TODO: Define Noble House Class
class NobleHouse :public House
{
public:
	NobleHouse();
	NobleHouse(std::string houseName);
	virtual ~NobleHouse() {}

	virtual void assignCharacter(Character* const character, Religion religion);
	virtual void dismissCharacter();

	virtual std::string getName() const;
	virtual Character* getDuellist() const;

	virtual int getSupporterCount() const;
	virtual void setSupporterCount(int supporterCount);

	virtual void increaseSupportCount();
	virtual void decreaseSupportCount();

	virtual void fight();

	virtual int getHouseType();
};

#endif // NOBLE_HOUSE_H