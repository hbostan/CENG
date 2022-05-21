#include "frame.h"

Frame::Frame()
{
	data = NULL;
	width = height = 0;
}

Frame::Frame(int width, int height)
{
	this->width = width;
	this->height = height;
	data = new Pixel[width*height];

}

Frame::Frame(int width, int height, const Pixel & p)
{
	this->width = width;
	this->height = height;

	data = new Pixel[width*height];

	for (int i = 0;i < (width*height);i++)
	{
		data[i].setR(p.getR());
		data[i].setG(p.getG());
		data[i].setB(p.getB());
	}
}

Frame::Frame(const Frame & rhs)
{
	width = rhs.getWidth();
	height = rhs.getHeight();
	if(rhs.data)
	{
		data = new Pixel[width*height];
		for (int j = 0;j < height;j++)
		{
			for (int i = 0; i < width;i++)
			{
				(*this)(i, j) = rhs(i, j);
			}
		}
	}
	else
		data = NULL;
}

Frame::~Frame()
{
	delete[] data;
	data = NULL;
	width = 0;
	height = 0;
}

Frame & Frame::operator=(const Frame & rhs)
{
	if(data) delete[] data;
	width = rhs.getWidth();
	height = rhs.getHeight();
	if(rhs.data)
	{
		data = new Pixel[width*height];
		for (int j = 0; j < height;j++)
		{
			for (int i = 0; i < width;i++)
			{
				(*this)(i, j) = rhs(i, j);
			}
		}
	}
	else
		data = NULL;
	return (*this);
}

Pixel & Frame::operator()(int x, int y)
{
	int index = (y*width) + x;
	return data[index];
}

const Pixel & Frame::operator()(int x, int y) const
{
	int index = (y*width) + x;
	return data[index];
}

Frame Frame::operator()(const Rect & rect) const
{
	Frame newFrame((rect.w), (rect.h));
	for (int j = rect.y; j < (rect.y + rect.h);j++)
	{
		for (int i = rect.x; i < (rect.x + rect.w);i++)
		{
			newFrame(i - rect.x, j - rect.y) = (*this)(i, j);
		}
	}

	return newFrame;
}

bool Frame::operator==(const Frame & rhs) const
{
	if (rhs.getHeight() == height && rhs.getWidth() == width)
	{
		for (int j = 0; j < height;j++)
		{
			for (int i = 0; i < width;i++)
			{
				for (int k = 0; k < 2;k++)
				{
					if ((*this)(i, j)[k] != rhs(i, j)[k]) return false;
				}
			}
		}
		return true;
	}
	else
	{
		return false;
	}
}

bool Frame::operator!=(const Frame & rhs) const
{
	if ((*this) == rhs) return false;
	else return true;
}

int Frame::getWidth() const
{
	return width;
}

int Frame::getHeight() const
{
	return height;
}

void Frame::clear(const Pixel & p)
{
	for (int j = 0; j < height;j++)
	{
		for (int i = 0;i < width;i++)
		{
			(*this)(i, j) = p;
		}
	}
}

void Frame::clear(const Rect & rect, const Pixel & p)
{
	for (int j = rect.y;j < (rect.y + rect.h);j++)
	{
		for (int i = rect.x; i < (rect.x + rect.w);i++)
		{
			(*this)(i, j) = p;
		}
	}
}

void Frame::crop(const Rect & rect)
{
	int newWidth = rect.w;
	int newHeight = rect.h;
	Pixel* newData = new Pixel[newHeight*newWidth];
	for (int j = rect.y;j < (rect.y + rect.h);j++)
	{
		for (int i = rect.x; i < (rect.x + rect.w);i++)
		{
			newData[((j - rect.y)*newWidth) + (i - rect.x)] = (*this)(i, j);
		}
	}
	delete[] data;
	height = newHeight;
	width = newWidth;
	data = newData;
}
