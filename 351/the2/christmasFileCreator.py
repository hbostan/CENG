# 07.12.2016: engin

import sys
import random

if len(sys.argv) < 5:
	sys.exit("Usage : %s giftCount outputFileName isSorted (0 or 1) isReversed (0 or 1)" % sys.argv[0])

giftCount = int(sys.argv[1])
outputFileName = sys.argv[2]
isSorted = int(sys.argv[3])
isReversed = int(sys.argv[4])

outputFile = open(outputFileName, "w")

giftNameList = ["Ball", "Car", "Barbie", "He-Man", "iPad", "iPhone", "MacBook", "Book"]
giftMaterialList = ["Plastic", "Silicon", "Wooden", "Metal", "Glass"]
giftColorList = ["Red", "Green", "Blue", "Space Gray", "Rose Gold", "Tornado Red", "White", "Black"]

gifts = []

delimiter = "|"

for i in range(giftCount):
	name = random.choice(giftNameList)
	material = random.choice(giftMaterialList)
	color = random.choice(giftColorList)

	fullString = str(i) + delimiter + name + delimiter + material + delimiter + color + "\n"

	gifts.append(fullString)

if isSorted == 1:
	random.shuffle(gifts)

if isReversed == 1:
	gifts = gifts[::-1]
	
for string in gifts:
	outputFile.write(string)

outputFile.close()