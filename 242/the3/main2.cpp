#include <iostream>
#include "video.h"

using namespace std;

int main()
{
    Video A, B;

    for (int i = 0; i < 20; ++i)
    {
        A << Frame(10, 10, Pixel((uchar) i, (uchar) i, (uchar) i));
    }

    Frame frame;

    A >> frame;
    if (frame(0, 0)[0] == 0)
    {
        cout << "Sucessfully read the zeroth frame from A" << endl;
    }

    A >> frame;
    if (frame(0, 0)[0] == 1)
    {
        cout << "Sucessfully read the first frame from A" << endl;
    }

    B = A;
    B >> frame;
    if (frame(0, 0)[0] == 2)
    {
        cout << "Sucessfully read the second frame from B" << endl;
    }
    
    A.resetStream();

    A >> frame;
    if (frame(0, 0)[0] == 0)
    {
        cout << "Sucessfully read the zeroth frame from A" << endl;
    }

    B >> frame;
    if (frame(0, 0)[0] == 3)
    {
        cout << "Sucessfully read the third frame from B" << endl;
    }

    B.dropFrame(4);
    B >> frame;
    if (frame(0, 0)[0] == 5)
    {
        cout << "Sucessfully read the fourth frame from B (fifth frame before the drop frame)" << endl;
    }

    B.trim(10, 12);
    B >> frame;
    if (frame(0, 0)[0] == 11)
    {
        cout << "Sucessfully read the zeroth frame from B (the originally eleventh frame before drop and trim)" << endl;
    }

    return 0;
}
