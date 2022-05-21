# 10.05.2016: engin
# Usage: python inputCreator.py -kingdomCount-
# Prints to stdout.
# Print to File: python inputCreator.py -kingdomCount- > ownInput.txt

# Noble House Names
nhCrownlands = ["Blount", "Brune", "Gaunt", "Rosby", "Rykker", "Slynt", "Stokeworth", "Thorne"]
nhNorth = ["Cerwyn", "Dormund", "Dustin", "Hornwood", "Karstark", "Manderly", "Mormont", "Ryswell", "Umber"]
nhRiverlands = ["Blackwood", "Bracken", "Mallister", "Mooton", "Smallwood"]
nhWesternlands = ["Crakehall", "Lannister of Lannisport", "Lefford", "Marbrand", "Payne", "Sarsfield", "Serrett", "Westerling", "Yarwyck"]
nhReach = ["Ashford", "Beesbury", "Bulwer", "Cuy", "Florent", "Fossoway", "Hightower", "Leygood", "Oakheart", "Redwyne", "Tarly"]
nhStormlands = ["Errol", "Estermont", "Grandison", "Morrigen", "Musgood", "Peasebury", "Tarth", "Trant", "Wylde"]
nhDorne = ["Blackmont", "Dayne", "Gargalen", "Jordayne", "Manwoody", "Qorgyle", "Uller", "Allyrion", "Yronwood"]
nhIronIslands = ["Blacktyde", "Botley", "Goodbrother", "Harlaw", "Kenning", "Merlyn", "Sparr"]
nhValeOfArryn = ["Corbray", "Egen", "Lynderly", "Hunter", "Moore", "Royce", "Tollett", "Waynwood"]
# Noble House Names

# Noble House Characters
pCrownlands = ["Boros Blount", "Lothor Brune", "Gwayne Gaunt", "Gyles Rosby", "Jaremy Rykker", "Janos Slynt", "Tanda Stokeworth", "Alliser Thorne"]
pNorth = ["Medger Cerwyn", "Marlin Dormund", "Barbrey Ryswell", "Halys Hornwood", "Rickard Karstark", "Wendel Manderly", "Jeor Mormont", "Barbrey Ryswell", "Greatjon Umber"]
pRiverlands = ["Tytos Blackwood", "Jonos Bracken", "Jason Mallister", "William Mooton", "Theomar Smallwood"]
pWesternlands = ["Desmond Crakehall", "Reginald Lannister", "Leo Lefford", "Damon Marbrand", "Ilyn Payne", "Eldrick Sarsfield", "Lord Serrett", "Gawen Westerling", "Othell Yarwyck"]
pReach = ["Lord Ashford", "Lyman Beesbury", "Jack Bulwer", "Emmon Cuy", "Axell Florent", "Lord Fossoway", "Leyton Hightower", "Lord Leygood", "Arys Oakheart", "Paxter Redwyne", "Randyll Tarly"]
pStormlands = ["Lord Errol", "Cassana Estermont", "Hugh Grandison", "Damon Morrigen", "Lord Musgood", "Lord Peasebury", "Brienne Tarth", "Meryn Trant", "Williem Wylde"]
pDorne = ["Larra Blackmont", "Beric Dayne", "Lord Gargalen", "Trebor Jordayne", "Dagos Manwoody", "Quentyn Qorgyle", "Mantral Uller", "Daemon Sand", "Edgar Yronwood"]
pIronIslands = ["Baelor Blacktyde", "Sawane Botley", "Urragon Goodbrother", "Alannys Harlaw", "Ralf Kenning", "Meldred Merlyn", "Lord Sparr"]
pValeOfArryn = ["Vance Corbray", "Vardis Egen", "Jon Lynderly", "Eon Hunter", "Mandon Moore", "Yohn Royce", "Eddison Tollett", "Anya Waynwood"]
# Noble House Characters

# Great House Characters
hCrownlands = ["Aerys II Targaryen", "Rhaegar Targaryen", "Aegon Targaryen", "Viserys Targaryen"]
hNorth = ["Brandon Stark", "Ned Stark", "Robb Stark", "Arya Stark", "Jon Snow", "Benjen Stark", "Roose Bolton", "Ramsay Bolton"]
hRiverlands = ["Walder Frey", "Stevron Frey", "Lothar Frey"]
hWesternlands = ["Tywin Lannister", "Jamie Lannister", "Kevan Lannister", "Tyrion Lannister", "Lancel Lannister"]
hReach = ["Luthor Tyrell", "Mace Tyrell", "Loras Tyrell"]
hStormlands = ["Robert Baratheon", "Stannis Baratheon", "Renly Baratheon", "Joffrey Baratheon"]
hDorne = ["Doran Martell", "Oberyn Martell", "Trystane Martell", "Obara Sand", "Nymeria Sand", "Elia Sand"]
hIronIslands = ["Balon Greyjoy", "Euron Greyjoy", "Theon Greyjoy", "Rodrik Greyjoy", "Victarion Greyjoy", "Aeron Greyjoy"]
hValeOfArryn = ["Jon Arryn", "Robin Arryn", "Petry Baelish"]
# Great House Characters

# Characters
nobleHouseNameList = [pCrownlands, pNorth, pRiverlands, pWesternlands, pReach, pStormlands, pDorne, pIronIslands, pValeOfArryn]
greatHouseNameList = [hCrownlands, hNorth, hRiverlands, hWesternlands, hReach, hStormlands, hDorne, hIronIslands, hValeOfArryn]
# Characters

# Kingdoms && Houses
nobleHouseList = [nhCrownlands, nhNorth, nhRiverlands, nhWesternlands, nhReach, nhStormlands, nhDorne, nhIronIslands, nhValeOfArryn]
greatHouseList = ["House Baratheon", "House Stark", "House Bolton", "House Frey", "House Lannister", "House Tyrell", "House Martell", "House Greyjoy", "House Arryn"]
kingdomList = ["The Crownlands", "The North", "The Riverlands", "The Westernlands", "The Reach", "The Stormlands", "Dorne", "The Iron Islands", "The Vale of Arryn"]
# Kingdoms && Houses

weaponListWizard = ["Ice Staff-1", "Fire Staff-2", "Lighting Staff-3", "Voodoo Staff-4"]
weaponListWhitewalker = ["Ice Blade-5"]
weaponListWildling = ["Longbow-6", "Dagger-7"] 

# Modifiers
weaponList = [weaponListWizard, weaponListWhitewalker, weaponListWildling]
armorList = ["Armor_None-0", "Leather-1", "Chainmail-2", "Platemail-3"]
religionList = ["Religion_None-0", "Faith of the Seven-1", "Old Gods of the Forest-2", "Drowned God-3", "Lord of Light-4", "God of Death-5", "Great Stallion-6", "God of Wits and Tine-7"]
# Modifiers

# Characters
characterList = ["Wizard", "Whitewalker", "Wildling"]
# Characters

import sys
import random

def createOutput(kingdomCount):
	if kingdomCount > len(kingdomList):
		sys.exit("Number cannot be bigger than %s" % len(kingdomList))
	elif kingdomCount <= 1:
		sys.exit("Number cannot be less than 2")

	print "Kingdom Number : %s" % kingdomCount
	print ""

	randomKingdomNumber = random.sample(list(range(0, len(kingdomList))), kingdomCount)

	for i in randomKingdomNumber:
		kingdomName = kingdomList[i]
		greatHouseName = greatHouseList[i]

		nobleHouseIndices = random.sample(list(range(0, len(nobleHouseList[i]))), 3)

		characterType = random.choice(characterList)

		print kingdomName + "/" + random.choice(religionList)
		print greatHouseName + "/" + random.choice(greatHouseNameList[i]) + "/" + characterType + "/" + random.choice(weaponList[characterList.index(characterType)]) + "/" + random.choice(armorList)

		for k in nobleHouseIndices:
			characterType = random.choice(characterList)
			print nobleHouseList[i][k] + "s/" + nobleHouseNameList[i][k] + "/" + characterType + "/" + random.choice(weaponList[characterList.index(characterType)]) + "/" + random.choice(armorList)

		print

if len(sys.argv) < 2:
	sys.exit("Usage : %s kingdom-count")

kingdomCount = int(sys.argv[1])

createOutput(kingdomCount)