package manager;

public class NodeTypewithScope
{
	public NodeType nt;
	public boolean player_scope;
	public boolean card_scope;
	public NodeTypewithScope() {}
	public NodeTypewithScope(NodeType nt_, boolean ps, boolean cs)
	{
		nt=nt_;
		player_scope=ps;
		card_scope=cs;
	}
}