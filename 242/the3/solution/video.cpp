#include "video.h"

Video::Video()
{
	frames = NULL;
	nFrames = 0;
	lastRead = 0;
}

Video::Video(const Video & rhs)
{
	nFrames = rhs.nFrames;
	frames = new Frame[nFrames];
	for (int i = 0; i < nFrames; i++)
	{
		frames[i] = rhs.frames[i];
	}
	lastRead = 0;

}

Video::~Video()
{
	delete[] frames;
	nFrames = 0;
	lastRead = 0;
}

Video & Video::operator=(const Video & rhs)
{
	
	if(frames) delete[] frames;
	nFrames = rhs.nFrames;
	lastRead = rhs.lastRead;
	if(rhs.frames)
	{
		frames = new Frame[nFrames];
		for (int i = 0; i < nFrames; i++)
		{
			frames[i] = rhs.frames[i];
		}
	}
	else
		frames = NULL;
		return (*this);
	
}

Video & Video::operator<<(const Frame & frame)
{
	if (frames != NULL)
	{
		Frame *newFrames = new Frame[nFrames + 1];
		int i;
		for (i = 0; i < nFrames; i++)
		{
			newFrames[i] = frames[i];
		}
		newFrames[i] = frame;
		delete[] frames;
		nFrames++;
		frames = newFrames;
	}
	if (frames == NULL)
	{
		Frame *newFrames = new Frame[1];
		newFrames[0] = frame;
		nFrames = 1;
		frames = newFrames;
	}
	return (*this);
}

Video & Video::operator>>(Frame & frame)
{
	frame = frames[lastRead];
	lastRead++;
	return (*this);
}

void Video::resetStream()
{
	lastRead = 0;
}

Frame Video::getFrame(int frameIndex) const
{
	Frame wanted = frames[frameIndex];
	return wanted;
}

void Video::dropFrame(int frameIndex)
{
	Frame* newFrames = new Frame[nFrames - 1];
	for (int i = 0; i < frameIndex; i++)
	{
		newFrames[i] = frames[i];
	}
	for (int i = frameIndex + 1; i < nFrames; i++)
	{
		newFrames[i - 1] = frames[i];
	}
	delete[] frames;
	frames = newFrames;
	nFrames--;
	if (frameIndex < lastRead) lastRead--;
}

void Video::trim(int startIndex, int endIndex)
{
	int newNFrames = endIndex - startIndex;
	Frame* newFrames = new Frame[newNFrames];

	for (int i = startIndex; i < endIndex; i++)
	{
		newFrames[i - startIndex] = frames[i];
	}

	if (lastRead > endIndex || lastRead < startIndex) lastRead = 0;
	else if (lastRead > startIndex && lastRead < endIndex) lastRead = lastRead - startIndex;

	delete[] frames;
	frames = newFrames;
	nFrames = newNFrames;


}
