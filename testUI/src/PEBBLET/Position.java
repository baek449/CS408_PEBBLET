package PEBBLET;

import manager.Node;

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
		return new Position(x+Node.xspace,y+Node.yspace);
	}
	public Position addLine()
	{
		return new Position(x,y+Node.yline);
	}
	public Position addTab()
	{
		return new Position(x+Node.xtab,y);
	}
	public Position add(Position p)
	{
		return new Position(x+p.x,y+p.y);
	}
}
