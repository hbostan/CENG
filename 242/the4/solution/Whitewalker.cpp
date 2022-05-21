#include "Whitewalker.h"

void Whitewalker::attackTo(Character * const character)
{
	character->takeDamage(attackPower);
	int frostLevel = character->getFrostBitten();
	if (++frostLevel > 3) frostLevel = 3;
	character->setFrostBitten(frostLevel);
}