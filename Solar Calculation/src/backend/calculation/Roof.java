package backend.calculation;

import java.util.List;

public class Roof {

	private Double length;
	private Double width;
	private Double angle;
	private int direction;
	private String name;
	private int sectionID;
	private int numberOfCurrentPanels;
	private int ageOfCurrentPanels;
	private Double sizeOfCurrentPanels;
	private int typeOfCurrentPanels;
	private List<Panel> Panels;

	public Roof(int sectionID, String name, Double length, Double width, Double angle, int direction, 
			int numberOfCurrentPanels, int ageOfCurrentPanels, Double sizeOfCurrentPanels, int typeOfCurrentPanels) {
		this.setName(name);
		this.setLength(length);
		this.setWidth(width);
		this.setAngle(angle);
		this.setDirection(direction);
		this.setSectionID(sectionID);
		this.setNumberOfCurrentPanels(numberOfCurrentPanels);
		this.setAgeOfCurrentPanels(ageOfCurrentPanels);
		this.setSizeOfCurrentPanels(sizeOfCurrentPanels);
		this.setTypeOfCurrentPanels(typeOfCurrentPanels);

	}
	
	public void addPanels(Panel panelSection){
		
		Panels.add(panelSection);
	}
	
	public Panel getPanel(int index){
		
		return Panels.get(index);
	}
	
	public int numberOfPanels(){
		
		return Panels.size();
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public int getSectionID() {
		return sectionID;
	}

	public void setSectionID(int sectionID) {
		this.sectionID = sectionID;
	}

	public int getNumberOfCurrentPanels() {
		return numberOfCurrentPanels;
	}

	public void setNumberOfCurrentPanels(int numberOfCurrentPanels) {
		this.numberOfCurrentPanels = numberOfCurrentPanels;
	}

	public int getAgeOfCurrentPanels() {
		return ageOfCurrentPanels;
	}

	public void setAgeOfCurrentPanels(int ageOfCurrentPanels) {
		this.ageOfCurrentPanels = ageOfCurrentPanels;
	}

	public Double getSizeOfCurrentPanels() {
		return sizeOfCurrentPanels;
	}

	public void setSizeOfCurrentPanels(Double sizeOfCurrentPanels) {
		this.sizeOfCurrentPanels = sizeOfCurrentPanels;
	}


	public Double getAngle() {
		return angle;
	}


	public void setAngle(Double angle) {
		this.angle = angle;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction2) {
		this.direction = direction2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTypeOfCurrentPanels() {
		return typeOfCurrentPanels;
	}

	public void setTypeOfCurrentPanels(int typeOfCurrentPanels2) {
		this.typeOfCurrentPanels = typeOfCurrentPanels2;
	}

}
