import java.io.*;

public class TestLog {
	private String log;
	public TestLog()
	{
		log="";
	}
	public String getLog()
	{
		return log;
	}
	public void setLog(String log_)
	{
		log=log_;
	}
	public boolean savelog(String directory)
	{
		try {
	        BufferedWriter out = new BufferedWriter(new FileWriter(directory));
	        out.write(log);
	        out.close();
	        return true;
	      } catch (IOException e) {
	          System.err.println(e);
	          return false;
	      }
	}
}
