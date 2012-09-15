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
		
		//Preparing to add the roof sections
		JsonArray jsonRoof = jsonHouse.getAsJsonArray("roofSection");
		
		Roof roofSection[] = new Roof[jsonRoof.size()];
		
		//Iterate through each roof section, creating its object and adding it to the array of roofs
		for (int i = 0; i < jsonRoof.size(); i++){
			
			JsonObject currentRoof = (JsonObject) jsonRoof.get(i);
			
			roofSection[i] = new Roof(i, currentRoof.get("sectionName").getAsString(), currentRoof.get("length").getAsDouble(),
					currentRoof.get("width").getAsDouble(), currentRoof.get("angle").getAsDouble(),
					currentRoof.get("direction").getAsInt(), currentRoof.get("numberOfCurrentPanels").getAsInt(),
					currentRoof.get("ageOfCurrentPanels").getAsInt(), currentRoof.get("sizeOfCurrentPanels").getAsDouble(),
					currentRoof.get("typeOfCurrentPanels").getAsInt());
		}
		
		//Setting the roof sections of the house
		house.setRoof(roofSection);
		
		
		double power = CalculatePower.calculate(house);
		
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
		out.println(JSONoutput);

	}
}
