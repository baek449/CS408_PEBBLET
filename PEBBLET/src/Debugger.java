
public class Debugger {
	public boolean execute()
	{
		if (!checkSyntaxError()) return false;
		if (!checkArithmeticError()) return false;
		if (!checkLogicalError()) return false;
		return true;
	}
	public boolean checkSyntaxError()
	{
		///// Logic for Syntax Error Checking
		return true;
	}
	public boolean checkArithmeticError()
	{
		///// Logic for Arithmetic Error Checking
		return true;
	}
	public boolean checkLogicalError()
	{
		///// Logic for Logical Error Checking
		return true;
	}
}
