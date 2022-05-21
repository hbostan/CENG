def cm(liste):
	if len(liste)==0:
		return [0,0,0]
	else:
		x=liste.pop(0)
		return tpl(cm(liste),skl(x))


def tpl(set1,set2):
	G1x=float(set1[0]); G1y=float(set1[1]); G1m=float(set1[2]);
	G2x=float(set2[0]); G2y=float(set2[1]); G2m=float(set2[2]);
	Dx=((G1x*G1m)+(G2x*G2m))/(G1m+G2m)
	Dy=((G1y*G1m)+(G2y*G2m))/(G1m+G2m)
	Dm=G1m+G2m
	return [Dx,Dy,Dm]


def skl(item):
	pi=3.1415926535897932
	if len(item)==4: #Daire
		Gx=float(item[1])
		Gy=float(item[2])
		Gm=pi*(float(item[3])**2.0)*int(item[0])
		
		
	if len(item)==7: #Ucgen
		x1=float(item[1]);	x2=float(item[3]);	x3=float(item[5]);
		y1=float(item[2]);	y2=float(item[4]);	y3=float(item[6]);
		Gx=(x1+x2+x3)/3.0
		Gy=(y1+y2+y3)/3.0
		Gm=(abs((x1*(y2-y3))+(x2*(y3-y1))+(x3*(y1-y2)))/2)*int(item[0])
		
		
	if len(item)==9: #Dikdortgen
		x1=float(item[1]);	x2=float(item[3]);	x3=float(item[5]);	x4=float(item[7])
		y1=float(item[2]);	y2=float(item[4]);	y3=float(item[6]);	y4=float(item[8])
		Gx=(float(item[1])+float(item[3])+float(item[5])+float(item[7]))/4.0
		Gy=(float(item[2])+float(item[4])+float(item[6])+float(item[8]))/4.0
		Gm=(abs((x1*(y2-y3))+(x2*(y3-y1))+(x3*(y1-y2))))*int(item[0])
	return [Gx,Gy,Gm]
	
