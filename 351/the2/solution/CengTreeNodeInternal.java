import java.util.ArrayList;

public class CengTreeNodeInternal extends CengTreeNode
{
	private ArrayList<Integer> keys;
	private ArrayList<CengTreeNode> children;	
	
	public CengTreeNodeInternal(CengTreeNode parent) 
	{
		super(parent);
		
		// TODO: Extra initializations, if necessary.
		this.type=CengNodeType.Internal;
		this.keys = new ArrayList<Integer>();
		this.children = new ArrayList<CengTreeNode>();

	}
	
	// GUI Methods - Do not modify
	public ArrayList<CengTreeNode> getAllChildren()
	{
		return this.children;
	}
	public Integer keyCount()
	{
		return this.keys.size();
	}
	public Integer keyAtIndex(Integer index)
	{
		if(index >= this.keyCount() || index < 0)
		{
			return -1;
		}
		else
		{
			return this.keys.get(index);			
		}
	}
	
	// Extra Functions
	public int search(Integer key)
	{
		int i=0;
		for(;i<this.keyCount();i++)
		{
			//System.out.println("Key: "+key + "Key At Index: " + keyAtIndex(i));
			if(key.equals(this.keyAtIndex(i)))
			{
				return i+1;
			}
			else if(key<keyAtIndex(i))
				return i;
		}
		return i;
	}

	public void insert(int index, Integer key, CengTreeNode left, CengTreeNode right)
	{
		if(keyCount()==0)
		{
			keys.add(index,key);
			children.add(index,left);
			children.add(index+1,right);
		}
		else if(keyCount()!=0)
		{
			keys.add(index,key);
			children.set(index, left);
			children.add(index+1,right);
		}


	}

	public CengTreeNode pushUp(Integer upKey, CengTreeNode left, CengTreeNode right) {
		int index = search(upKey);

		insert(index,upKey,left,right);

		if(keyCount()>2*order)
		{
			//System.out.println("SPASD");
			return this.overflow();
		}
		else
		{
			if(getParent()==null)return this;
			else return null;
		}
	}

	public CengTreeNodeInternal split()
	{
		int mid	= keyCount()/2;
		int keyC = keyCount();
		CengTreeNodeInternal right = new CengTreeNodeInternal(null);
		/*for(int i=keyC-1;i>mid;i--)
		{
			right.keys.add(this.keys.get(i));
			this.keys.remove(i);
		}*/
		for(int i=mid+1;i<keyC;i++)
		{
			right.keys.add(this.keys.get(i));
		}

		for(int i=mid+1;i<=keyC;i++)
		{
			//System.out.println(i+" "+children.size());
			right.children.add(this.children.get(i));
			right.getAllChildren().get(i-mid-1).setParent(right);

		}
		for(int i=keyC-1;i>mid;i--)
			this.keys.remove(i);
		for(int i=keyC;i>mid;i--)
			this.children.remove(i);
		this.keys.remove(mid);
		return right;
	}

	public CengTreeNode overflow()
	{
		int mid = keyCount()/2;
		Integer upKey = keyAtIndex(mid);

		CengTreeNodeInternal right = this.split();

		if(this.getParent()==null)
		{
			this.setParent(new CengTreeNodeInternal(null));
		}
		right.setParent(this.getParent());

		return ((CengTreeNodeInternal)this.getParent()).pushUp(upKey, this, right);
	}

	public void printNode(int depth)
	{
		String spaces ="";
		for(int i=0;i<depth;i++) spaces+="\t";
		System.out.println(spaces+"<index>");
		for(int i=0;i<keyCount();i++) System.out.println(spaces+keys.get(i));
		System.out.println(spaces+"</index>");
		for(int i=0;i < children.size();i++)
		{
			children.get(i).printNode(depth+1);
		}
	}

}
