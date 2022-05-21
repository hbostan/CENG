#include "Editor.h"

	void Editor::insertLine()
	{
        Action act(INSERT_LINE,getCursorRow(),getCursorCol(),0);
        Line newLine;
        lines.insertNode(lines.getNodeAt(cursor),newLine);
        cursor++;
        history.push(act);
	}


	void Editor::insertChar(char c)
    {
        Action act(INSERT_CHAR,getCursorRow(),getCursorCol(),0);
        lines.getNodeAt(cursor)->getData()->insert(c);
        history.push(act);
    }

	void Editor::del()
    {
        char para;
        para=lines.getNodeAt(cursor)->getData()->getCharAt(getCursorCol());

        Action act(DEL,getCursorRow(),getCursorCol(),para);

        lines.getNodeAt(cursor)->getData()->del();

        if(para) history.push(act);
    }

	void Editor::backspace()
    {
        int lcb,lca;
        char para;
        para=lines.getNodeAt(cursor)->getData()->getCharAt(getCursorCol()-1);
        Action act(BACKSPACE,getCursorRow(),getCursorCol(),para);
        lcb=getCursorCol();
        lines.getNodeAt(cursor)->getData()->backspace();
        lca=getCursorCol();
        if(lca!=lcb) history.push(act);
    }

	void Editor::undo()
    {
        if(!history.empty())
        {
            ActionType at=(history.top()).getType();
            int ccol=(history.top()).getCol();
            int crow=(history.top()).getRow();
            char param=(history.top()).getParam();

            switch(at)
            {
                case INSERT_LINE:
                    cursor=crow;
                    lines.deleteNode(lines.getNodeAt(cursor+1));
                    break;
                case INSERT_CHAR:
                    cursor=crow;
                    lines.getNodeAt(cursor)->getData()->moveCursorTo(ccol);
                    lines.getNodeAt(cursor)->getData()->del();
                    break;
                case DEL:
                    cursor=crow;
                    lines.getNodeAt(cursor)->getData()->moveCursorTo(ccol);
                    lines.getNodeAt(cursor)->getData()->insert(param);
                    lines.getNodeAt(cursor)->getData()->moveCursorTo(ccol);
                    break;
                case BACKSPACE:
                    cursor=crow;
                    lines.getNodeAt(cursor)->getData()->moveCursorTo(ccol-1);
                    lines.getNodeAt(cursor)->getData()->insert(param);
                    break;
            }
            history.pop();
        }
    }
	/* Moves the cursor up by 1 if the cursor is not at the first line.
	 */
	void Editor::moveCursorUp()
    {
        if((lines.getNodeAt(getCursorRow())!=lines.getHead())&&(cursor!=0))cursor--;
    }
	/* Moves the cursor down by 1 if the cursor is not at the last line.
	 */
	void Editor::moveCursorDown()
    {
        if(lines.getNodeAt(getCursorRow())!=lines.getTail()) cursor++;
    }
	/* Calls corresponding function from Line class
	 */
	void Editor::moveCursorLeft()
    {
        lines.getNodeAt(getCursorRow())->getData()->moveCursorLeft();
    }
	/* Calls corresponding function from Line class
	 */
	void Editor::moveCursorRight()
    {
        lines.getNodeAt(getCursorRow())->getData()->moveCursorRight();
    }
	/* Returns cursor row(global cursor) under 0-based numbering.
	 */
	int Editor::getCursorRow() const
    {
        return cursor;
    }
	/* Returns cursor column(cursor of the line that global cursor is at)
	 * under 0-based numbering.
	 */
	int Editor::getCursorCol() const
    {
        return lines.getNodeAt(getCursorRow())->getData()->getCursorPosition();
    }
	/* Returns line count of the editor
	 */
	int Editor::getLength() const
    {
        int i=0;
        while(lines.getNodeAt(i)!=lines.getTail())
        {
            i++;
        }
        return i+1;

    }
	/* Returns the content of the idx-th line under 0-based numbering.
	 */
	std::string Editor::getLine(int idx) const
    {
        Line *theline = lines.getNodeAt(idx)->getData();
        int max=theline->getLength();
        std::string thestring;
        for(int i=0;i<max;i++)
        {
            thestring+=theline->getCharAt(i);
        }
        return thestring;
    }
