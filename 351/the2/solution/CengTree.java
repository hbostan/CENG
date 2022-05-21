import sun.net.www.http.ChunkedOutputStream;

import java.util.ArrayList;

public class CengTree
{
	public CengTreeNode root;
	
	public CengTree(Integer order)
	{
		CengTreeNode.order = order;

		// TODO: Create an empty Tree
		root = new CengTreeNodeLeaf(null);
		// System.out.println(root.getType());

	}
	
	public void addGift(CengGift gift)
	{

		// TODO: Insert Gift to Tree

		CengTreeNodeLeaf leaf = findProperNode(gift);

		leaf.insertGift(gift);
		if(leaf.giftCount()>2*CengTreeNode.order)
		{
			CengTreeNode newNode = leaf.overflow();
			if(newNode != null)
			{
				root=newNode;
			}
		}

	}

	public CengTreeNodeLeaf findProperNode(CengGift gift)
	{
		CengTreeNode curNode = root;
		while(curNode.getType() == CengNodeType.Internal)
		{
			curNode = ((CengTreeNodeInternal) curNode).getAllChildren().get( ((CengTreeNodeInternal) curNode).search(gift.key()));
		}

		return (CengTreeNodeLeaf) curNode;
	}
	
	public ArrayList<CengTreeNode> searchGift(Integer key)
	{
		// TODO: Search within whole Tree, return visited nodes.
		// Return null if not found.
		ArrayList<CengTreeNode> visited = new ArrayList<>();
		CengTreeNode curNode = root;
		while(curNode.getType() == CengNodeType.Internal)
		{
			visited.add(curNode);
			curNode = ((CengTreeNodeInternal) curNode).getAllChildren().get( ((CengTreeNodeInternal) curNode).search(key));
		}
		if(curNode.getType()== CengNodeType.Leaf)
		{
			if(((CengTreeNodeLeaf)curNode).search(key) != -1)
			{
				visited.add(curNode);
				for(int i=0;i<visited.size();i++)
				{
					CengTreeNode curVisit = visited.get(i);
					if(curVisit.getType()==CengNodeType.Internal)
					{
						CengTreeNodeInternal internalNode = (CengTreeNodeInternal)curVisit;
						String spaces="";
						for(int k=0;k<i;k++) spaces+="\t";

						System.out.println(spaces+"<index>");
						for(int j=0; j<internalNode.keyCount(); j++)
						{
							System.out.println(spaces + internalNode.keyAtIndex(j));
						}
						System.out.println(spaces + "</index>");

					}
					else if(curVisit.getType()==CengNodeType.Leaf)
					{
						CengTreeNodeLeaf leafNode = (CengTreeNodeLeaf) curVisit;
						Integer index =((CengTreeNodeLeaf)curNode).search(key);
						String spaces="";
						for(int k=0;k<i;k++) spaces+="\t";
						System.out.println(spaces+"<record>"+leafNode.getGifts().get(index).fullName()+"</record>");

					}
				}
				return visited;
			}
			else
			{
				System.out.println("Could not find " + key + ".");
			}
		}

		return null;
	}
	
	public void printTree()
	{
		// TODO: Print the whole tree to console
		root.printNode(0);
	}

	// Extra Functions

}
