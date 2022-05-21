def IsOperator(item):
	if item in ("+","-","*","/","^"):
		return True
	else:
		return False
		
def IsOperand(item):
	if (ord(item)>=ord("a")) and (ord(item)<=ord("z")):
		return True
	else:
		return False

def IsRightAssociative(op):
	if op=="^":
		return True
	else:
		return False

def EarthianHasHigherPrecedence(op1,op2):
	op1weight=EarthianGetOperatorWeight(op1)
	op2weight=EarthianGetOperatorWeight(op2)
	if op1weight==op2weight:
		if IsRightAssociative(op1):
			return False
		else:
			return True
	elif op1weight>op2weight:
		return True
	else:
		return False

def ZoitankianHasHigherPrecedence(op1,op2):
	op1weight=ZoitankianGetOperatorWeight(op1)
	op2weight=ZoitankianGetOperatorWeight(op2)
	if op1weight==op2weight:
		if IsRightAssociative(op1):
			return False
		else:
			return True
	elif op1weight>op2weight:
		return True
	else:
		return False

def EarthianGetOperatorWeight(op):
	weight=-1
	if op in ("+","-"):
		weight=1
	if op in ("*","/"):
		weight=2
	if op in ("^"):
		weight=3
	return weight

def ZoitankianGetOperatorWeight(op):
	weight=-1
	if op in ("+"):
		weight=5
	if op in ("/"):
		weight=4
	if op in ("-"):
		weight=3
	if op in ("*"):
		weight=2
	if op in ("^"):
		weight=1
	return weight

def EarthianInfixToPostfix(inp):
	postfix=[]
	stack=[]
	i=0
	while i<len(inp):
		if inp[i]==" " or inp[i]=="," :
			continue
		elif (IsOperator(inp[i])):
			while (len(stack)!=0 and stack[-1]!="(" and EarthianHasHigherPrecedence(stack[-1],inp[i])):
				postfix.append(stack.pop(-1))
			stack.append(inp[i])
		elif (IsOperand(inp[i])):
			postfix.append(inp[i])
		elif inp[i]=="(":
			stack.append(inp[i])
		elif inp[i]==")":
			while len(stack)!=0 and stack[-1]!="(":
				postfix.append(stack.pop(-1))
			stack.pop(-1)
		i=i+1
			
	while len(stack)!=0:
		postfix.append(stack.pop(-1))
	
	#return "".join(postfix)
	return postfix

def ZoitankianInfixToPostfix(inp):
	postfix=[]
	stack=[]
	i=0
	while i<len(inp):
		if inp[i]==" " or inp[i]=="," :
			cointiue
		elif (IsOperator(inp[i])):
			while (len(stack)!=0 and stack[-1]!="(" and ZoitankianHasHigherPrecedence(stack[-1],inp[i])):
				postfix.append(stack.pop(-1))
			stack.append(inp[i])
		elif (IsOperand(inp[i])):
			postfix.append(inp[i])
		elif inp[i]=="(":
			stack.append(inp[i])
		elif inp[i]==")":
			while len(stack)!=0 and stack[-1]!="(":
				postfix.append(stack.pop(-1))
			stack.pop(-1)
		i=i+1
			
	while len(stack)!=0:
		postfix.append(stack.pop(-1))
	
	#return "".join(postfix)
	return postfix

def infixer(inp): #verilen postfixi parantezli infix'e cevir
	leng=len(inp)
	count=0
	while leng>=3:
		if inp[count]=="/":
			inp[count]="*"
		elif inp[count]=="*":
			inp[count]="/"
		if inp[count] in ("^","*","/","-","+"):
			derp="("+inp[count-2]+inp[count]+inp[count-1]+")"
			del inp[count]
			del inp[count-1]
			del inp[count-2]
			inp.insert(count-2,derp)
			leng-=2
			count=-1
		count += 1
	"""if leng==3:
		
		if inp[2]=="/":
			inp[2]="*"
		
		elif inp[2]=="*":
			inp[2]="/"
		derp="("+inp[0]+inp[2]+inp[1]+")"
		del inp[2]
		del inp[1]
		del inp[0]
		inp.insert(0,derp)"""
		
	result="".join(inp)
	return result	

def ToZoitankian(inp):
	return infixer(EarthianInfixToPostfix(inp))

def ToEarthian(inp):
	return infixer(ZoitankianInfixToPostfix(inp))

