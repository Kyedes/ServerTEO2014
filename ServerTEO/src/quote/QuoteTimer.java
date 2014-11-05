package quote;

import java.util.Timer;
import java.util.TimerTask;

public class QuoteTimer {
	
	Timer timer = new Timer();
	Quotes quoteModel = new Quotes();
	
	private void quoteDatabaseUpdate(){
		while (true)
		{
			timer.schedule(new TimerTask() {
				  @Override
				  public void run() {
					  
				  quoteModel.saveQuote();
				  }
				  }
			, (long)0, (long) 1440*60*1000);
		}

}
}