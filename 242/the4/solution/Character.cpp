#include "Character.h"

Character::Character(string characterName)
{
	name = characterName;
	abbreviation = "-NONE-";

	health = 0;
	attackPower = 0;

	bleedingLevel = 0;
	frostBittenLevel = 0;
	takingMagicalDamageLevel = 0;

	weaponBns = 0;
	armorBns = 0;
	religionArmorBns = 0;
	religionWeaponBns = 0;
}

string Character::getName() const
{
	return name;
}

string Character::getAbbreviation() const
{
	return abbreviation;
}

int Character::getHealth() const
{
	return health;
}

int Character::getAttackPower() const
{
	return attackPower;
}

int Character::getBleeding() const
{
	return bleedingLevel;
}

void Character::setBleeding(int bleedingLevel)
{
	this->bleedingLevel = bleedingLevel;
}

int Character::getFrostBitten() const
{
	return frostBittenLevel;
}

void Character::setFrostBitten(int frostBittenLevel)
{
	this->frostBittenLevel = frostBittenLevel;
}

int Character::getTakingMagicalDamage() const
{
	return takingMagicalDamageLevel;
}

void Character::setTakingMagicalDamage(int takingMagicalDamageLevel)
{
	this->takingMagicalDamageLevel = takingMagicalDamageLevel;
}

void Character::takeDamage(int damage)
{
	health -= damage;
}

void Character::applyCheerBonus(int damageBonus)
{
	attackPower += damageBonus;
}

void Character::restoreOriginalValues()
{
	health = healthDefault;
	attackPower = attackPowerDefault;
}

void Character::removeDebuffs()
{
	bleedingLevel = 0;
	frostBittenLevel = 0;
	takingMagicalDamageLevel = 0;
}

void Character::saveOriginalValues()
{
	healthDefault = health;
	attackPowerDefault = attackPower;
}

void Character::applyDOTs()
{
	health -= (bleedingLevel*BLEEDING_DAMAGE + frostBittenLevel*FROSTBITE_DAMAGE + takingMagicalDamageLevel*MAGICAL_DAMAGE);
}

bool Character::isDead()
{
	if (health <= 0) return true;
	else return false;
}

void Character::setWeapon(Weapon weapon)
{
	attackPower -= weaponBns;
	weaponBns = weaponValue(weapon);
	attackPower += weaponBns;
	saveOriginalValues();
}

void Character::setArmor(Armor armor)
{
	health -= armorBns;
	armorBns= armorValue(armor);
	health += armorBns;
	saveOriginalValues();
}

void Character::setReligion(Religion religion)
{
	attackPower -= religionWeaponBns;
	health -= religionArmorBns;

	religionWeaponBns = religionValue(religion).first;
	religionArmorBns = religionValue(religion).second;

	attackPower += religionWeaponBns;
	health += religionArmorBns;

	saveOriginalValues();
}
