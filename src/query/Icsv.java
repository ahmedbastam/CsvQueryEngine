package query;

import java.io.IOException;

import javax.script.ScriptException;

public interface Icsv {
	
	
	public String[] getColumnList();
	public void getColumn(String column);
	public void getRow(int index);
	public String[] getLineVal(String line);
	public String getFileName();
	public void processFile(String predicat, Result res, String[] wantedColumns) throws IOException,ScriptException;
	

}
