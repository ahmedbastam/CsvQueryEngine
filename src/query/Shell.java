package query;

import java.util.Scanner;

public class Shell {

	public static void main(String[] args) throws Exception {
		
		System.out.println("Welcome to the CSV Query Engine");
		String query ="";
		
		Query qr = Query.getInstance();
		
		Scanner sc = new Scanner(System.in);
		
		while(!query.equals("EXIT") || query==null || query=="") {
			
			query = sc.nextLine();
			
			if(query.length()!=0) qr.setQuery(query);
			
			}
		sc.close();
		
		
		
		
	}

}
