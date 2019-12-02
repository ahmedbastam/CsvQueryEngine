package query;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class CsvFile implements Icsv {
	
	private String[] columnList;
	private String fileName;
	private MappedByteBuffer mbb;
	private byte[] by ;
	
	public CsvFile(String path) throws Exception {
		
		Path filePath = Paths.get(path);

		//Open file channel for read 
		try (FileChannel fc = (FileChannel.open(filePath,StandardOpenOption.READ))) {
            //Get file channel in read-only mode
             
            //Get direct byte buffer access using channel.map() operation
            this.mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            
            by = new byte[(int)fc.size()];
    		mbb.get(by);
    		
    		BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(by)));
    		this.columnList=in.readLine().split(",");
    	    in.close();
            
        }
		
		String[] name = path.split("/");
		this.fileName=name[name.length-1];
		
	}
	
	
	public String[] getColumnList() {
		return this.columnList;
	}
	
	
	public void getColumn(String column) {
		}
	
	public void getRow(int index) {
		
	}
	
	
	
	public String[] getLineVal(String line) {
		return line.split(",");
	}
	
	public String getFileName() {
		return this.fileName;
	}
	
	public void processFile(String predicate, Result res, String[] wantedColumns) throws IOException,ScriptException{
		
		//this method execute the SELECT query on the csv file 
		
		BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(by)));
		
		
	    int count = 0;

		while(true) {
			
			ScriptEngineManager mgr = new ScriptEngineManager();
		    ScriptEngine engine = mgr.getEngineByName("JavaScript"); 
		    //ScriptEngineManager is used to read the predicate as boolean expression 

			String pr = predicate;

			String line = in.readLine();
			if(line==null || line.length()==0) break;

			String[] val= line.split(",");
			for(int i=0;i<val.length;i++) {
				engine.put("val"+i, val[i]);
				
			}
			
			//System.out.println(pr);
			
			boolean bool = Boolean.valueOf(engine.eval(pr).toString());
			
			if(bool) {
				count++;
				
				if(count ==1) {
					String fr="";

					for(int i =0;i<wantedColumns.length;i++) {
						fr=fr+"%-17s";

					}
					System.out.format(fr+"\n",(Object[]) wantedColumns);
					System.out.println();
					
				}
				res.setRow(val);
				res.print();
			}
		    
		}
		if(count ==0) System.out.println("no rows");
		in.close();

		
		
	}
}
