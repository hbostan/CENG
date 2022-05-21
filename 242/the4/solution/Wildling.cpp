#include "Wildling.h"

void Wildling::attackTo(Character * const character)
{
	character->takeDamage(attackPower);
	int bleedLevel = character->getBleeding();
	if (++bleedLevel > 3) bleedLevel = 3;
	character->setBleeding(bleedLevel);
}