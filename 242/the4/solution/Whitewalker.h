#ifndef WHITEWALKER_H
#define WHITEWALKER_H

#include "Character.h"

// TODO: Define Whitewalker Class
class Whitewalker :public Character
{
public:
	Whitewalker() :Character("noName")
	{
		abbreviation = "-WHI-";

		attackPower = 40;
		health = 600;
		saveOriginalValues();
	}

	Whitewalker(std::string name) : Character(name)
	{
		abbreviation = "-WHI-";

		attackPower = 40;
		health = 600;
		saveOriginalValues();
	}
	~Whitewalker() {}

	virtual void attackTo(Character* const character);

};
#endif // WHITEWALKER_H