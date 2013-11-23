
public class RuleManager {
	private Rule rule;
	public Node search(int[] location)
	{
		Node cur=rule.getRoot();
		int loop;
		for(loop=0;loop<location.length;loop++)
		{
			cur=cur.getChildNode(location[loop]);
		}
		return cur;
	}
	public void modify(int[] location,Node n)
	{
		Node cur=search(location).getParent();
		cur.getAllNode().set(location[location.length-1], n);
	}
	public Rule getRule()
	{
		return rule;
	}
	public void setRule(Rule rule_)
	{
		rule=rule_;
	}
}
