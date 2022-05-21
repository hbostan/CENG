#include "Wizard.h"

void Wizard::attackTo(Character * const character)
{
	character->takeDamage(attackPower);
	int magicLevel = character->getTakingMagicalDamage();
	if(++magicLevel > 3) magicLevel=3;
	character->setTakingMagicalDamage(magicLevel);
}

