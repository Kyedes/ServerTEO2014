package model.Forecast;

import java.sql.SQLException;

import com.google.gson.Gson;

import shared.WeatherReturObject;

public class Weather {
	private WeatherReturObject wro = new WeatherReturObject();
	private ForecastModel fm = new ForecastModel();
	private Gson gson = new Gson();
	
	public String getWeather() throws SQLException{
		String answer = "";
		wro.setWeather(fm.getForecast());
		
		answer = gson.toJson(wro);
		
		return answer;
	}
}
