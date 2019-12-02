package query;

import java.util.HashMap;


public class Query {
	
	private Result res ;
	private HashMap<String,Icsv> files ;
	private String query ;
	
	private static  Query qr ;
	
	//we can only have one instance of this class
	private Query() {
		
		files = new HashMap<String,Icsv>();
		
	}
	
	public static  Query getInstance() {
		
		if(qr==null) {
			
			qr = new Query();
			
		}
		return qr;
	}
	
	
	
	public void addFile(Icsv file) {
		
		files.put(file.getFileName(), file);
	}
	
	
	public void setQuery(String line) throws Exception{
		
		//this method is responsible of executing the query 
		
		this.query = line.replaceAll(";", "");
		
		if(this.type() == 'i') {
			
			Icsv file = new CsvFile(this.getPath('i'));
			this.files.put(file.getFileName(), file);
			System.out.println("File imported!");
			
		}
		
		else if(this.type()=='s') {
			res = new Result(this.getWantedColumns('s'),files.get(this.getFileName('s')).getColumnList(),this.getFileName('s'));
			String predicat = this.getPredicate('s');
			
			

			
			
			files.get(this.getFileName('s')).processFile(predicat, res, this.getWantedColumns('s'));
			
			
		}
		else {
				
				System.out.println("End of program .");
			} 
		
		
	}
	
	public char type() {
		
		//returns the type of the query : s for SELECT , i for IMPORT , e for EXIT
		
		char type=query.toLowerCase().charAt(0);
		return type;
		
	}
	
	public String getPath (char type) {
		
		//this method extract the file path from the IMPORT query
		
		if(type=='i') {
			String path = this.query.split("IMPORT ",0)[1];
			return path;
		}
		else return null;
		
	}
	
	public String[] getWantedColumns(char type){
		
		//this method returns the list of columns in the SELECT query
		
		if(type=='s') {
			
			String t1 = this.query.split("SELECT ",0)[1];
			String t2 = t1.split(" FROM ",0)[0];
			String[] t3 = t2.split(",");
			for(int i=0;i<t3.length;i++) {
				t3[i]=t3[i].trim();
			}
			
			return t3;
			
		}
		else return null;
	}
	
	public String getFileName(char type) {
		
		//returns the fileName in the SELECT query
		
		if(type=='s') {
			
			String t1 = this.query.split(" FROM ")[1];
			String t2 = t1.split(" WHERE ")[0];
			return t2.trim();
			
		}
		else return null;
		
	}
	
	public String getPredicate(char type) {
		
		//get the predicate from the SELECT query and modify it to became a boolean expression
		
		if(type=='s') {
			
			String t1 = this.query.split(" WHERE ")[1];	
			
			t1=t1.replaceAll("AND", "&&");
			t1=t1.replaceAll("OR", "||");
			t1=t1.replaceAll("=", "==");
			
			String fname = this.getFileName('s');
			String[] columnList = files.get(fname).getColumnList();
			
			int i=0;
			for(String e : columnList) {
				t1=t1.replaceAll(e,"val"+i);
				i++;
				
			}
			
			return t1;
			
			
			
			
			
			
			
		}
		else return null;
		
	}
	
	
	
	
}
