#ifndef WILDLING_H
#define WILDLING_H

#include "Character.h"

// TODO: Define Wildling Class
class Wildling :public Character
{
public:
	Wildling() :Character("noName")
	{
		abbreviation = "-WIL-";

		attackPower = 70;
		health =425;
		saveOriginalValues();
	}

	Wildling(std::string name) : Character(name)
	{
		abbreviation = "-WIL-";

		attackPower = 70;
		health = 425;
		saveOriginalValues();
	}
	~Wildling() {}

	virtual void attackTo(Character* const character);

};
#endif // WILDLING_H