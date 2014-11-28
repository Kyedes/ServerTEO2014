

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import shared.QuoteObject;
import model.QueryBuild.QueryBuilder;

import com.google.gson.Gson;

public class Quotes {
	private QueryBuilder qb = new QueryBuilder();
	private QuoteObject quote = new QuoteObject();
	private QuoteObject quoteReturn = new QuoteObject();
	private ResultSet resultSet;
	private Gson gson = new Gson();

	/**
	 *
	 */ 

	private static String readUrl(String urlString) throws Exception {
		BufferedReader reader = null;
		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read);
			return buffer.toString();
		} finally {
			if (reader != null)
				reader.close();
		}

	}

	public void importQuote() throws SQLException{

		/**
		 * getting text from website and putiing into string
		 * Making a new object of JSON, and prints out quote
		 */
		String json = null;
		
			try {
				json = readUrl("http://dist-sso.it-kartellet.dk/quote/");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			quote = (QuoteObject) gson.fromJson(json, QuoteObject.class);
			
			String[] fields = {
    				"quote",
    				"author",
    				"subject"
    		};
    		String[] values = {
    				quote.getQuote(),
    				quote.getAuthor(),
    				quote.getSubject()
    		};
    		
    		resultSet = qb.selectFrom("quote").all().ExecuteQuery();
    		if(resultSet.next()){
    			qb.update("quote", fields, values).all().Execute();
    		}else{
    			qb.insertInto("quote", fields).values(values).Execute();
    		}
	}

	/**
	 * Retrieve Quote from a website and put it into a String, 
	 * Afterwards we will make it into a json object so it can be printed out to the client.
	 */
	
	public String getQuote() throws SQLException{
		
		Date date = new Date();
		
		String answer = "";
		
		resultSet = qb.selectFrom("quote").all().ExecuteQuery();
		
		if(resultSet.next()){
			if(resultSet.getLong("lastUpdate")+86400000 < date.getTime()){//if more than a day since last update, then update the quote
				importQuote();
				qb.update("quote",new String[] {"lastUpdate"},new String[] {String.format(""+date.getTime())}).all().Execute();
			}
			quoteReturn.setQuote(resultSet.getString("quote"));
			quoteReturn.setAuthor(resultSet.getString("author"));
			quoteReturn.setSubject(resultSet.getString("subject"));
			
		}else{
			quoteReturn.setQuote("The quote database is not available right now.");
			quoteReturn.setAuthor("Esben Kyed, System Programmer");
			quoteReturn.setSubject("Error");
		}
		
		answer = (String) gson.toJson(quoteReturn);
		
		return answer;
	}


//	public void updateQuote() throws Exception{
//		Date date = new Date(); // Current date & time
//		long maxTimeNoUpdate = 86400; // Maximum one day with no update
//
//		long date1 = date.getTime();
//		long date2 = date.getTime() - maxTimeNoUpdate; // minus 1 hour -- should be fetched from database
//
//		long timeSinceUpdate = date1 - date2; 
//
//
//		// if more than 1 hour ago, do update
//		if(timeSinceUpdate > 864000){
//			// return fresh weather data
//			importQuote();
//		}
//	}

}
