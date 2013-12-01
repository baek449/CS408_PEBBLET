package PEBBLET;

public class AreaRange {
	public int sx;
	public int sy;
	public int ex;
	public int ey;
	public boolean valid;
	public boolean inside(int x_, int y_)
	{
		if(x_<sx) return false;
		if(x_>=ex) return false;
		if(y_<sy) return false;
		if(y_>=ey) return false;
		return true;
	}
	public boolean inside(Position p)
	{
		return inside(p.x,p.y);
	}
	public AreaRange(int sx_, int sy_, int ex_, int ey_, boolean valid_)
	{
		sx=sx_;
		sy=sy_;
		ex=ex_;
		ey=ey_;
		valid=valid_;
	}
	public AreaRange(AreaRange a)
	{
		sx=a.sx;
		sy=a.sy;
		ex=a.ex;
		ey=a.ey;
		valid=a.valid;
	}
	public AreaRange(Position start, Position end, boolean valid_)
	{
		sx=start.x;
		sy=start.y;
		ex=end.x;
		ey=end.y;
		valid=valid_;
	}
	
	public boolean isvalid()
	{
		return valid;
	}
	
	public Position nextRight()
	{
		if (sx==ex && sy==ey) return getStart();
		return new Position(ex+NodeDisplayer.xdist,sy);
	}
	public Position nextBottom()
	{
		if (sx==ex && sy==ey) return getStart();
		return new Position(sx,ey+NodeDisplayer.ydist);
	}
	public Position getStart()
	{
		return new Position(sx,sy);
	}
	public Position getEnd()
	{
		return new Position(ex,ey);
	}
	public void enclose()
	{
		sx-=NodeDisplayer.xspace;
		sy-=NodeDisplayer.yspace;
		ex+=NodeDisplayer.xspace;
		ey+=NodeDisplayer.yspace;
	}
	public AreaRange merge(AreaRange a)
	{
		if(sx>a.sx) sx=a.sx;
		if(sy>a.sy) sy=a.sy;
		if(ex<a.ex) ex=a.ex;
		if(ey<a.ey) ey=a.ey;
		return this;
	}
	public AreaRange merge(Position end)
	{
		if(ex<end.x) ex=end.x;
		if(ey<end.y) ey=end.y;
		return this;
	}
}
