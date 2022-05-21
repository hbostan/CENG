#include "pixel.h"

Pixel::Pixel(uchar r, uchar g, uchar b)
{
	red = r;
	green = g;
	blue = b;
}

uchar & Pixel::operator[](int index)
{
	switch (index)
	{
	case 0: return red;
	case 1: return green;
	case 2: return blue;
	}
}

const uchar & Pixel::operator[](int index) const
{
	switch (index)
	{
	case 0: return(red); break;
	case 1: return(green); break;
	case 2: return(blue); break;
	}
}

uchar Pixel::getR() const
{
	return red;
}

uchar Pixel::getG() const
{
	return green;
}

uchar Pixel::getB() const
{
	return blue;
}

void Pixel::setR(uchar r)
{
	red = r;
}

void Pixel::setG(uchar g)
{
	green = g;
}

void Pixel::setB(uchar b)
{
	blue = b;
}

std::ostream & operator<<(std::ostream & os, const Pixel & p)
{
	os << "(" << (int)(p.getR()) << "," << (int)(p.getG()) << "," << (int)(p.getB()) << ")";
	return os;
}
