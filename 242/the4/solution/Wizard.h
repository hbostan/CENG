#ifndef WIZARD_H
#define WIZARD_H

#include "Character.h"

// TODO: Define Wizard Class
class Wizard :public Character
{
public:
	Wizard() :Character("noName")
	{
		abbreviation = "-WIZ-";

		attackPower = 60;
		health = 450;
		saveOriginalValues();
	}

	Wizard(std::string name) : Character(name)
	{
		abbreviation = "-WIZ-";

		attackPower = 60;
		health = 450;
		saveOriginalValues();
	}
	~Wizard() {}

	virtual void attackTo(Character* const character);

};

#endif // WIZARD_H