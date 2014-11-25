import java.sql.SQLException;

import Shared.LogInObject;
import Shared.LogInReturnObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.QueryBuild.QueryBuilder;
import model.Model;

public class LogIn extends Model{

	private QueryBuilder qb = new QueryBuilder();
	private Gson gson = new GsonBuilder().create();
	private LogInReturnObject logInRO = new LogInReturnObject();

	/**
	 * Allows the client to log in
	 * @param email
	 * @param password
	 * @param isAdmin
	 * @return answer
	 * @throws Exception
	 */

	public String execute(LogInObject logInObject) throws SQLException{

		String answer = "";

		String result = authenticateUser(logInObject.getAuthUsername(), logInObject.getAuthPassword(), logInObject.getIsAdmin());
		
		switch (result){
		case "1":
			logInRO.setLogOn(false);
			logInRO.setExplanation("The email or password is incorect.");
			break;
		case "2":
			logInRO.setLogOn(false);
			logInRO.setExplanation("The user is inactive; contact an admin to resolve the issue.");
			break;
		case "3":
			logInRO.setLogOn(false);
			logInRO.setOverallID("brugertype ikke stemmer overens med loginplatform.");
			break;
		case "0":
			logInRO.setLogOn(true);
			logInRO.setExplanation("Logon succesfull.");
			break;
		default:
			logInRO.setLogOn(false);
			logInRO.setExplanation("System error.");
			break;

		}
		
		answer = gson.toJson(logInRO);

		return answer;
	}

	public String authenticateUser(String email, String password, boolean isAdmin) throws SQLException {

		String[] keys = {"userid", "email", "active", "password"};

		//		qb = new QueryBuilder();

		// Henter info om bruger fra database via querybuilder
		resultSet = qb.selectFrom(keys, "users").where("email", "=", email).ExecuteQuery();

		// Hvis en bruger med forespurgt email findes
		if (resultSet.next())
		{
			// Hvis passwords matcher
			if(resultSet.getString("password").equals(password))
			{
				// If the user is activ
				if(resultSet.getBoolean("active"))
				{
					String userID = resultSet.getString("userid");

					String[] key = {"type"};

					resultSet = qb.selectFrom(key, "roles").where("userid", "=", userID).ExecuteQuery();

					// Hvis brugeren baade logger ind og er registreret som admin, eller hvis brugeren baade logger ind og er registreret som bruger
					if((resultSet.getString("type").equals("admin") && isAdmin) || (resultSet.getString("type").equals("user") && !isAdmin))
					{
						return "0"; // returnerer "0" hvis bruger/admin er godkendt
					} else {
						return "3"; // returnerer fejlkoden "3" hvis brugertype ikke stemmer overens med loginplatform
					}
				} else {
					return "2"; // returnerer fejlkoden "2" hvis bruger er sat som inaktiv
					
				}
			} else {
				return "1"; // returnerer fejlkoden "1" hvis email ikke findes eller hvis password og username ikke matcher
			}
		} else {
			return "1"; // returnerer fejlkoden "1" hvis email ikke findes eller hvis password og username ikke matcher
		}

	}

}
