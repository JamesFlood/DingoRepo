package backend.calculation;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;

public class House {

	private String Country = "";
	private String Currency = "";
	private Double lat;
	private Double lon;
	private Float wire;
	private String electricityCompany = "";
	private Double tariff;
	private int[] usage;
	private Double cost;
	private Roof Roofs[];
	
	public void setLocation(String Country) {
		this.Country=Country;
	}

	public String getCountry() {

		return Country;
	}
	
	public void setRoof(Roof Roof[]) {
		
		Roofs = Roof;
	}
	
	public Roof getRoof(int index){
		
		return Roofs[index];
	}
	
	public int numberOfRoofs(){
		
		return Roofs.length;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public Float getWire() {
		return wire;
	}

	public void setWire(Float wire) {
		this.wire = wire;
	}

	public String getElectricityCompany() {
		return electricityCompany;
	}

	public void setElectricityCompany(String electricityCompany) {
		this.electricityCompany = electricityCompany;
	}

	public Double getTariff() {
		return tariff;
	}

	public void setTariff(Double tariff) {
		this.tariff = tariff;
	}

	public int getUsage(int i) {
		return usage[i];
	}

	public void setUsage(int[] usage) {
		this.usage = usage;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

}
