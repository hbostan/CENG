#ifndef MAIN_CPP
#define MAIN_CPP

#include <iostream>
#include <fstream>

using std::ifstream;

#include "GreatHouse.h"
#include "NobleHouse.h"

#include "Wizard.h"
#include "Whitewalker.h"
#include "Wildling.h"

#include "Kingdom.h"

#include "War.h"

void overloadCharacter(Character *character, int characterChoice)
{
	character->setWeapon(WEAPON_NONE);
	character->setArmor(ARMOR_NONE);

	if(characterChoice == 1)
	{
		// Wizard

		character->setWeapon(ICESTAFF);
		character->setWeapon(FIRESTAFF);
		character->setWeapon(LIGHTINGSTAFF);
		character->setWeapon(VOODOSTAFF);
		character->setWeapon(LIGHTINGSTAFF);
		character->setWeapon(FIRESTAFF);
		character->setWeapon(ICESTAFF);

		character->setWeapon(VOODOSTAFF);

		character->setArmor(CHAINMAIL);
		character->setArmor(PLATEMAIL);
		character->setArmor(CHAINMAIL);
	}
	else
	{
		// Wildling

		character->setWeapon(LONGBOW);
		character->setWeapon(DAGGER);
		character->setWeapon(LONGBOW);

		character->setArmor(LEATHER);
		character->setArmor(CHAINMAIL);
		character->setArmor(PLATEMAIL);
		character->setArmor(CHAINMAIL);
		character->setArmor(LEATHER);
	}
}
void saveCharacter(Character *character)
{
	character->saveOriginalValues();
}
int main(int argv, char** argc)
{
	War *sharedWar = new War();

	// Part 1 - Creation(s)

	Kingdom *theNorth = new Kingdom("The North", GOD_OF_WITS_AND_TINE);
	Kingdom *theCrownlands = new Kingdom("The Crownlands", FAITH_OF_THE_SEVEN);

	GreatHouse *houseStark = new GreatHouse("House Stark");
	GreatHouse *houseBaratheon = new GreatHouse("House Baratheon");

	NobleHouse *houseKarstark = new NobleHouse("House Karstark");
	NobleHouse *houseManderly = new NobleHouse("House Manderly");
	NobleHouse *houseMormont = new NobleHouse("House Mormont");

	NobleHouse *houseSlynt = new NobleHouse("House Slynt");
	NobleHouse *houseThorne = new NobleHouse("House Thorne");
	NobleHouse *houseGaunt = new NobleHouse("House Gaunt");

	Wizard *starkWizard = new Wizard("Stark");
	Wildling *baratheonWildling = new Wildling("Baratheon");

	Wizard *karstarkWizard = new Wizard("Karstark");
	Wizard *manderlyWizard = new Wizard("Manderly");
	Wizard *mormontWizard = new Wizard("Mormont");

	Wildling *slyntWildling = new Wildling("Slynt");
	Wildling *thorneWildling = new Wildling("Thorne");
	Wildling *gauntWildling = new Wildling("Gaunt");

	vector<Character *> starkCharacters;
	starkCharacters.push_back(starkWizard);
	starkCharacters.push_back(karstarkWizard);
	starkCharacters.push_back(manderlyWizard);
	starkCharacters.push_back(mormontWizard);

	vector<Character *> baratheonCharacters;
	baratheonCharacters.push_back(baratheonWildling);
	baratheonCharacters.push_back(slyntWildling);
	baratheonCharacters.push_back(thorneWildling);
	baratheonCharacters.push_back(gauntWildling);

	// Part 2 & 3 - Weapon/Armor Overloading

	for(int i = 0; i < starkCharacters.size(); i++)
	{
		overloadCharacter(starkCharacters.at(i), 1);
		overloadCharacter(baratheonCharacters.at(i), 2);
	}

	// Part 4 - Assignments

	houseStark->assignCharacter(starkWizard, GOD_OF_WITS_AND_TINE);
	houseKarstark->assignCharacter(karstarkWizard, GOD_OF_WITS_AND_TINE);
	houseManderly->assignCharacter(manderlyWizard, GOD_OF_WITS_AND_TINE);
	houseMormont->assignCharacter(mormontWizard, GOD_OF_WITS_AND_TINE);

	houseBaratheon->assignCharacter(baratheonWildling, FAITH_OF_THE_SEVEN);
	houseSlynt->assignCharacter(slyntWildling, FAITH_OF_THE_SEVEN);
	houseThorne->assignCharacter(thorneWildling, FAITH_OF_THE_SEVEN);
	houseGaunt->assignCharacter(gauntWildling, FAITH_OF_THE_SEVEN);

	theNorth->assignGreatHouse(houseStark);
	theNorth->addNobleHouse(houseKarstark);
	theNorth->addNobleHouse(houseManderly);
	theNorth->addNobleHouse(houseMormont);

	theCrownlands->assignGreatHouse(houseBaratheon);
	theCrownlands->addNobleHouse(houseSlynt);
	theCrownlands->addNobleHouse(houseThorne);
	theCrownlands->addNobleHouse(houseGaunt);

	for(int i = 0; i < starkCharacters.size(); i++)
	{
		//saveCharacter(starkCharacters.at(i));
		//saveCharacter(baratheonCharacters.at(i));
	}

	// Disabling cout
	std::cout.setstate(std::ios_base::failbit);

	pair<int, int> characterDuelResult = sharedWar->duelOfCharacters(starkWizard, baratheonWildling, 0, 0);
	int houseDuelResult = sharedWar->duelOfHouses(houseKarstark, houseThorne);
	int kingdomDuelResult = sharedWar->duelOfKingdoms(theNorth, theCrownlands);

	// Enabling cout
    std::cout.clear();

    cout << "------------------------------" << endl;
    cout << "Stark Wizard vs Baratheon Wildling" << endl;
    switch(characterDuelResult.first)
    {
    	case 1:
    		cout << "Stark Wizard has won." << endl;
    		break;
    	case 0:
    		cout << "Character Duel Draw." << endl;
    		break;
    	case -1:
    		cout << "Baratheon Wildling has won." << endl;
    		break;
    }
    cout << "------------------------------" << endl;
    cout << "House Karstark vs House Thorne" << endl;
    switch(houseDuelResult)
    {
    	case 1:
    		cout << "House Karstark has won." << endl;
    		break;
    	case 0:
    		cout << "House Duel Draw." << endl;
    		break;
    	case -1:
    		cout << "House Thorne has won." << endl;
    		break;
    }
    cout << "------------------------------" << endl;
    cout << "The North vs The Crownlands" << endl;
    switch(houseDuelResult)
    {
    	case 1:
    		cout << "The North has won." << endl;
    		break;
    	case 0:
    		cout << "Kingdom Duel Draw." << endl;
    		break;
    	case -1:
    		cout << "The Crownlands has won." << endl;
    		break;
    }
    cout << "------------------------------" << endl;
}

#endif