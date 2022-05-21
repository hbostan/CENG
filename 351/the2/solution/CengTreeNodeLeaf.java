import java.util.ArrayList;

public class CengTreeNodeLeaf extends CengTreeNode
{
	private ArrayList<CengGift> gifts;
	
	public CengTreeNodeLeaf(CengTreeNode parent) 
	{
		super(parent);
		
		// TODO: Extra initializations
		this.type=CengNodeType.Leaf;
		this.gifts = new ArrayList<CengGift>();
		//System.out.println(this.gifts.size());
	}
	
	// GUI Methods - Do not modify
	public int giftCount()
	{
		return gifts.size();
	}
	public Integer giftKeyAtIndex(Integer index)
	{
		if(index >= this.giftCount())
		{
			return -1;
		}
		else
		{
			CengGift gift = this.gifts.get(index);
			
			return gift.key();
		}
	}

	public int search(Integer searchKey)
	{
		for(int i=0; i<this.giftCount(); i++)
		{
			if(gifts.get(i).key().equals(searchKey) )
			{
				return i;
			}
			else if( searchKey < gifts.get(i).key() )
				return -1;
		}
		return -1;
	}

	public void insertGift(CengGift gift)
	{
		int i = 0;
		while (i < giftCount() && gift.key() > gifts.get(i).key())
			i++;
		gifts.add(i,gift);
	}

	public CengTreeNodeLeaf split()
	{
		int mid = giftCount()/2;

		CengTreeNodeLeaf right = new CengTreeNodeLeaf(null);
		for(int i=giftCount()-1;i>=mid;i--)
		{
			right.insertGift(this.gifts.get(i));
			this.gifts.remove(i);
		}
		return right;
	}
	public ArrayList<CengGift> getGifts(){
		return gifts;
	}
	// Extra Functions
	public CengTreeNode overflow()
	{
		int mid = giftCount()/2;
		Integer upKey = gifts.get(mid).key();

		CengTreeNodeLeaf right = split();

		if(getParent()==null)
		{
			setParent(new CengTreeNodeInternal(null));
		}
		right.setParent(this.getParent());

		return ((CengTreeNodeInternal)this.getParent()).pushUp(upKey, this, right);
	}

	public void printNode(int depth)
	{
		String spaces ="";
		for(int i=0;i<depth;i++) spaces+="\t";
		System.out.println(spaces+"<data>");
		for(int i=0;i<gifts.size();i++) System.out.println(spaces+"<record>" + gifts.get(i).fullName() + "</record>");
		System.out.println(spaces+"</data>");
	}
}
