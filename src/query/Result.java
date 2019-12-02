package query;

import java.util.HashMap;

public class Result {
	private String[] row ;
	private String[] wantedColumns ;
	private HashMap<String,Integer> hm ;
	private String fileName;
	
	
	Result(String[] wantedColumns , String[] allColumns, String fileName){
		
		hm= new HashMap<String,Integer>();
		int i=0;
		for(String e : allColumns) {
			hm.put(e, i);
			i++;
		}
		
		this.wantedColumns = wantedColumns;
		this.fileName=fileName;
		
		
	}
	
	public void print() {
		String fr="";

		for(int i =0;i<this.row.length;i++) {
			fr=fr+"%-17s";

		}
		System.out.format(fr+"\n",(Object[]) this.row);

		
	}
	
	public void setRow (String[] row) {
		
		this.row = new String[wantedColumns.length];
		int i =0;
		for(String e:wantedColumns) {
			
			this.row[i]=row[hm.get(e)];
			i++;
		}
		
	}
	
	
	

}
