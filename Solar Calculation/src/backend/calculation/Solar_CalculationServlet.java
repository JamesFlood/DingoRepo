package backend.calculation;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.*;

import com.google.gson.*;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@SuppressWarnings("serial")
public class Solar_CalculationServlet extends HttpServlet {
	
	House house = new House();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/ plain");
		
		//Receiving the JSON parameter and forming a jsonObject from the house
		JsonObject json = (JsonObject) new JsonParser().parse(req.getParameter("jsonParameter"));
		JsonObject jsonHouse = json.getAsJsonObject("house");
		
		//Setting all the house attributes to our house object
		house.setLocation(jsonHouse.get("country").toString());
		house.setCurrency(jsonHouse.get("currency").toString());
		house.setLat(jsonHouse.get("lat").getAsDouble());
		house.setLon(jsonHouse.get("lon").getAsDouble());
		house.setWire(jsonHouse.get("wire").getAsFloat());
		house.setElectricityCompany(jsonHouse.get("electricityCompany").toString());
		house.setTariff(jsonHouse.get("tariff").getAsDouble());
		house.setCost(jsonHouse.get("cost").getAsDouble());

		//Building the usagePerQuarter array
		JsonArray jsonUsage = jsonHouse.getAsJsonArray("usagePerQuarter");
		int usage[] = new int[5];
		
		for (int i = 0; i < 4; i++)
		{
			usage[i] = jsonUsage.get(i).getAsInt();
		}
		
		usage[4] = usage[0] + usage[1] + usage[2] + usage[3];
		
		//Setting the usagePerQuarter array
		house.setUsage(usage);
		
		System.out.println("HELLO");
		
		//Preparing to add the roof sections
		JsonArray jsonRoof = jsonHouse.getAsJsonArray("roofSection");
		
		Roof roofSection[] = new Roof[jsonRoof.size()];
		
		System.out.println("hi");
		
		//Iterate through each roof section, creating its object and adding it to the array of roofs
		for (int i = 0; i < jsonRoof.size(); i++){
			
			JsonObject currentRoof = (JsonObject) jsonRoof.get(i);
			
			System.out.println(i);
			
			roofSection[i] = new Roof(i, currentRoof.get("sectionName").getAsString(), 
					currentRoof.get("length").getAsDouble(), currentRoof.get("width").getAsDouble(), currentRoof.get("angle").getAsDouble(),
					currentRoof.get("direction").getAsInt(), currentRoof.get("numberOfCurrentPanels").getAsInt(),
					currentRoof.get("ageOfCurrentPanels").getAsInt(), currentRoof.get("sizeOfCurrentPanels").getAsDouble(),
					currentRoof.get("typeOfCurrentPanels").getAsInt());
			
		}
		
		//Setting the roof sections of the house
		house.setRoof(roofSection);
		
		
		String actualJson = "{\"result\": [";
		
		//Setting the scenarios
		JsonArray jsonScenarios = json.getAsJsonArray("scenarios");
		Scenario scenarios[] = new Scenario[jsonScenarios.size()];
		
		JsonObject jsonResponse = new JsonObject();
		JsonArray results = new JsonArray();
		
		for (int i = 0; i < jsonScenarios.size(); i++){
			
			JsonObject jsonScenario = jsonScenarios.get(i).getAsJsonObject();
			JsonArray jsonPanelsPerSection = jsonScenario.getAsJsonArray("numberOfPanelsPerSection");
			int numberOfPanelsPerSection[] = new int[jsonPanelsPerSection.size()];
			
			for (int n = 0; n < jsonPanelsPerSection.size(); n++){
				
				numberOfPanelsPerSection[n] = jsonPanelsPerSection.get(n).getAsInt();
			}
			
			scenarios[i] = new Scenario(jsonScenario.get("name").getAsString(), jsonScenario.get("typeOfNewPanels").getAsInt(),
					jsonScenario.get("sizeOfNewPanels").getAsInt(), numberOfPanelsPerSection, 
					jsonScenario.get("quotedPrice").getAsDouble());
			
			JsonObject result = new JsonObject();
			result.addProperty("name", scenarios[i].getName());
			result.addProperty("quotedPrice", 0);
			
			double powerResult = CalculatePower.calculate(house);
			double powerQuart = powerResult / 4;
			
			actualJson = actualJson + "{" +
			      "\"name\": \"" + scenarios[i].getName() + "\"," +
			      "\"quotedPrice\": 0," +
			      "\"quarterlyResults\": [" +
			        "{" +
			          "\"powerGenerated\": " + powerQuart + "," +
			          "\"powerUsed\": " + house.getUsage(0) + "," +
			          "\"powerBought\": 0," +
			          "\"excessPowerGenerated\": " + (powerQuart - house.getUsage(0)) + "" +
			        "}," +
			        "{" +
			          "\"powerGenerated\": " + powerQuart + "," +
			          "\"powerUsed\": " + house.getUsage(1) + "," +
			          "\"powerBought\": 0," +
			          "\"excessPowerGenerated\": " + (powerQuart - house.getUsage(1)) + "" +
			        "}," +
			        "{" +
			          "\"powerGenerated\": " + powerQuart + "," +
			          "\"powerUsed\": " + house.getUsage(2) + "," +
			          "\"powerBought\": 0," +
			          "\"excessPowerGenerated\": " + (powerQuart - house.getUsage(2)) + "" +
			        "}," +
			        "{" +
			          "\"powerGenerated\": " + powerQuart + "," +
			          "\"powerUsed\": " + house.getUsage(3) + "," +
			          "\"powerBought\": 0," +
			          "\"excessPowerGenerated\": " + (powerQuart - house.getUsage(3)) + "" +
			        "}" +
			      "]," +
			      "\"powerGeneratedPerYear\": " + powerResult + "," +
			      "\"powerUsedPerYear\": " + house.getUsage(4) + "," +
			      "\"powerBoughtPerYear\": 0," +
			      "\"excessPowerGeneratedPerYear\": " + ((powerQuart - house.getUsage(0)) + (powerQuart - house.getUsage(1))
			      + (powerQuart - house.getUsage(2)) + (powerQuart - house.getUsage(3))) + "," +
			      "\"breakEvenTime\": {" +
			        "\"years\": 0," +
			        "\"months\": 0" +
			      "}," +
			      "\"moneySavedAfter1Year\": " + (powerResult * house.getTariff()) + "," +
			      "\"moneySavedAfter5Years\": " + ((powerResult * house.getTariff()) * 5) + "," +
			      "\"graphURL\": \"http://www.graph.com\"" +
			    "}";
			
			if ((i + 1) < jsonScenarios.size()){
				
				actualJson = actualJson + ",";
			}
			
		}//End Scenario For Loop
		
		actualJson += "],\"status\": \"valid\"}";
		
		
		
		
		
		
		
		double power = CalculatePower.calculate(house);
		
		System.out.println(power);
		
		String JSONoutput = "{\"result\":[{\"name\":\"budget\",\"quotedPrice\":0,\"quarterlyResults\":[{\"powerGenerated\":" +
				"1234,\"powerUsed\":1234,\"powerBought\":1234,\"excessPowerGenerated\":1234},{\"powerGenerated\":1234,\"" +
				"powerUsed\":1234,\"powerBought\":1234,\"excessPowerGenerated\":1234},{\"powerGenerated\":1234,\"powerUsed\"" +
				":1234,\"powerBought\":1234,\"excessPowerGenerated\":1234},{\"powerGenerated\":1234,\"powerUsed\":1234,\"" +
				"powerBought\":1234,\"excessPowerGenerated\":1234}],\"powerGeneratedPerYear\":1234,\"powerUsedPerYear\":1234" +
				",\"powerBoughtPerYear\":1234,\"excessPowerGeneratedPerYear\":1234,\"breakEvenTime\":{\"years\":2,\"months\":" +
				"3},\"moneySavedAfter1Year\":1234,\"moneySavedAfter5Years\":1234,\"graphURL\":\"http://www.graph.com\"},{\"" +
				"name\":\"crazy\",\"quotedPrice\":0,\"quarterlyResults\":[{\"powerGenerated\":1234,\"powerUsed\":1234,\"" +
				"powerBought\":1234,\"excessPowerGenerated\":1234},{\"powerGenerated\":1234,\"powerUsed\":1234,\"powerBought\"" +
				":1234,\"excessPowerGenerated\":1234},{\"powerGenerated\":1234,\"powerUsed\":1234,\"powerBought\":1234,\"" +
				"excessPowerGenerated\":1234},{\"powerGenerated\":1234,\"powerUsed\":1234,\"powerBought\":1234,\"" +
				"excessPowerGenerated\":1234}],\"powerGeneratedPerYear\":1234,\"powerUsedPerYear\":1234,\"powerBoughtPerYear" +
				"\":1234,\"excessPowerGeneratedPerYear\":1234,\"breakEvenTime\":{\"years\":2,\"months\":3},\"" +
				"moneySavedAfter1Year\":1234,\"moneySavedAfter5Years\":1234,\"graphURL\":\"http://www.graph.com\"}],\"status\":" +
				"\"valid\"}";
		
		PrintWriter out = resp.getWriter();
		out.println(actualJson);

	}
}
