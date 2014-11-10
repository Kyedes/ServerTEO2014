import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.QueryBuild.QueryBuilder;
import model.Model;

public class LogIn extends Model{

	private QueryBuilder qb = new QueryBuilder();
	private Gson gson = new GsonBuilder().create();

	/**
	 * Allows the client to log in
	 * @param email
	 * @param password
	 * @param isAdmin
	 * @return answer
	 * @throws Exception
	 */

	public String execute(LogInObject logInObject) throws Exception{

		String answer = "";

		String result = authenticateUser(logInObject.getAuthUsername(), logInObject.getAuthPassword(), logInObject.getIsAdmin());

		LogInReturnObject returnO = new LogInReturnObject();


		switch (result){
		case "1":
			returnO.setLogOn(false);
			returnO.setExplanation("The email or password is incorect.");
			break;
		case "2":
			returnO.setLogOn(false);
			returnO.setExplanation("The user is inactive; contact an admin to resolve the issue.");
			break;
		case "3":
			returnO.setLogOn(false);
			returnO.setOverallID("brugertype ikke stemmer overens med loginplatform.");
			break;
		case "0":
			returnO.setLogOn(true);
			returnO.setExplanation("Logon succesfull.");
			break;
		default:
			returnO.setLogOn(false);
			returnO.setExplanation("System error.");
			break;

		}
		
		answer = gson.toJson(returnO);

		return answer;
	}

	public String authenticateUser(String email, String password, boolean isAdmin) throws Exception {

		String[] keys = {"userid", "email", "active", "password"};

		//		qb = new QueryBuilder();

		// Henter info om bruger fra database via querybuilder
		resultSet = qb.selectFrom(keys, "users").where("email", "=", email).ExecuteQuery();

		// Hvis en bruger med forespurgt email findes
		if (resultSet.next()){


			// Hvis passwords matcher
			if(resultSet.getString("password").equals(password))
			{
				// If the user exists the if statement continues:
				if(resultSet.getBoolean("active"))
				{
					int userID = resultSet.getInt("userid");

					String[] key = {"type"};

					resultSet = qb.selectFrom(key, "roles").where("userid", "=", new Integer(userID).toString()).ExecuteQuery();

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
				return "1"; // returnerer fejlkoden "1" hvis password eller username ikke matcher
			}
		} else {
			return "1"; // returnerer fejlkoden "1" hvis email eller password ikke findes
		}

	}

}
