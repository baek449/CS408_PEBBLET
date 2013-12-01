package PEBBLET;

public class Position {
	public int x;
	public int y;
	public Position(int x_, int y_)
	{
		x=x_;
		y=y_;
	}
	public Position add(int dx, int dy)
	{
		return new Position(x+dx,y+dy);
	}
	public Position addSpace()
	{
		return new Position(x+NodeDisplayer.xspace,y+NodeDisplayer.yspace);
	}
	public Position addLine()
	{
		return new Position(x,y+NodeDisplayer.yline);
	}
	public Position addTab()
	{
		return new Position(x+NodeDisplayer.xtab,y);
	}
	public Position add(Position p)
	{
		return new Position(x+p.x,y+p.y);
	}
}
