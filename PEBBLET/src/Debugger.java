
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
		
		///// Syntax empty
		
		///// Syntax duplicate
		
		///// Syntax type error
		
	}
	public boolean checkArithmeticError()
	{
		///// Logic for Arithmetic Error Checking
		return true;
		///// NAN divide by 0
		
		///// No implementation 
		
		///// out of boundary
		
	}
	public boolean checkLogicalError()
	{
		///// Logic for Logical Error Checking
		return true;
	}
}
