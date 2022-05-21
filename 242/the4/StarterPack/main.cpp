#ifndef MAIN_CPP
#define MAIN_CPP

#include <sstream>

#include "GreatHouse.h"
#include "NobleHouse.h"

#include "Wizard.h"
#include "Whitewalker.h"
#include "Wildling.h"

#include "Kingdom.h"

#include "War.h"

int main(int argv, char** argc)
{
	War *warSimulation = new War();

	for(unsigned k = 0; k < 2; k++)
	{
		std::ostringstream s1;
		s1 << "Kingdom" << k + 1;

		Kingdom *kingdom = new Kingdom(s1.str(), FAITH_OF_THE_SEVEN);

		GreatHouse *greatHouse = new GreatHouse("GreatHouse1");

		Character *greatHouseHero = new Wizard("WizardGH");

		greatHouseHero->setWeapon(ICESTAFF);
		greatHouseHero->setArmor(PLATEMAIL);

		greatHouse->assignCharacter(greatHouseHero, kingdom->getReligion());

		// Save after Weapon && Armor && Religion value is set.
		greatHouseHero->saveOriginalValues();

		kingdom->assignGreatHouse(greatHouse);

		for(unsigned int i = 0; i < 3; i++)
		{
			std::ostringstream s2;
			s2 << "NobleHouse" << i + 1;

			NobleHouse *nobleHouse = new NobleHouse(s2.str());

			std::ostringstream s3;

			Character *nobleHouseHero;

			switch(i)
			{
				case 0:
					s3 << "WizardNH" << i + 1;
					nobleHouseHero = new Wizard(s3.str());
					nobleHouseHero->setWeapon(ICESTAFF);
					nobleHouseHero->setArmor(CHAINMAIL);
					break;
				case 1:
					s3 << "WhitewalkerNH" << i + 1;
					nobleHouseHero = new Whitewalker(s3.str());
					nobleHouseHero->setWeapon(ICEBLADE);
					nobleHouseHero->setArmor(ARMOR_NONE);
					break;
				case 2:
					s3 << "WildlingNH" << i + 1;
					nobleHouseHero = new Wildling(s3.str());
					nobleHouseHero->setWeapon(LONGBOW);
					nobleHouseHero->setArmor(LEATHER);
					break;
			}

			// Save after Weapon && Armor && Religion value is set.
			nobleHouse->assignCharacter(nobleHouseHero, kingdom->getReligion());

			nobleHouseHero->saveOriginalValues();

			kingdom->addNobleHouse(nobleHouse);
		}

		warSimulation->addKingdom(kingdom);
	}

	warSimulation->startSimulation();
}

#endif