package backend.calculation;

public class Scenario {


	private String name;
	private int typeOfNewPanel;
	private int sizeOfNewPanels;
	private int[] numberOfPanelsPerSection;
	private Double quotedPrice;
	
	public Scenario (String name, int typeOfNewPanel, int sizeOfNewPanels, int[] numberOfPanelsPerSection, Double quotedPrice){
		
		this.setName(name);
		this.setTypeOfNewPanel(typeOfNewPanel);
		this.setSizeOfNewPanels(sizeOfNewPanels);
		this.setNumberOfPanelsPerSection(numberOfPanelsPerSection);
		this.setQuotedPrice(quotedPrice);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTypeOfNewPanel() {
		return typeOfNewPanel;
	}
	public void setTypeOfNewPanel(int typeOfNewPanel) {
		this.typeOfNewPanel = typeOfNewPanel;
	}
	public int getSizeOfNewPanels() {
		return sizeOfNewPanels;
	}
	public void setSizeOfNewPanels(int sizeOfNewPanels) {
		this.sizeOfNewPanels = sizeOfNewPanels;
	}
	public int[] getNumberOfPanelsPerSection() {
		return numberOfPanelsPerSection;
	}
	public void setNumberOfPanelsPerSection(int[] numberOfPanelsPerSection) {
		this.numberOfPanelsPerSection = numberOfPanelsPerSection;
	}
	public Double getQuotedPrice() {
		return quotedPrice;
	}
	public void setQuotedPrice(Double quotedPrice) {
		this.quotedPrice = quotedPrice;
	}
	
	
	
	
}
