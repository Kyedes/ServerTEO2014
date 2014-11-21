

import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class QuoteTimer {
	
	Timer timer = new Timer();
	Quotes quoteModel = new Quotes();
	
	private void quoteDatabaseUpdate(){
		while (true)
		{
			timer.schedule(new TimerTask(){
				  @Override
				  public void run(){
					  
				  try {
					quoteModel.importQuote();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  }
				  }
			, (long)0, (long) 1440*60*1000);
		}

}
}