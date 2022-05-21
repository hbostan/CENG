def flatten(tree): #Leaf Bulmaca
	ret=[]
	if len(tree)==1:
		 return [tree[0].lower()]
	else:
		x=1
		while x<len(tree):
			ret += flatten(tree[x])
			x += 1
		return ret

def splitsentence(sentence): #Cumle Duzeltmece
	result=sentence.split(" ")
	for item in range(len(result)):
		result[item]=result[item].lower()
	return result
	
def indictionary(grammar,sentence,dictionary): #Dictionaryde Aramaca
	if len(grammar)==0 or len(sentence)==0:
		return True
	if len(grammar)!=len(sentence):
		return False
	return (sentence[0] in dictionary[grammar[0]]) and indictionary(grammar[1:],sentence[1:],dictionary)

def fixdictionary(D): #Dictionary Duzeltmece
	newdict={}
	for item in D:
		words=[]
		x=0
		while x<len(D[item]):
			words.append(D[item][x].lower())
			x+=1
		newdict[item.lower()]=words
	return newdict
	
def is_valid(T,D,S):
	grammar=flatten(T)
	dictionary=fixdictionary(D)
	sentence=splitsentence(S)
	return indictionary(grammar,sentence,dictionary)
	
