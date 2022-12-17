package sk.upjs.ics.chladnicka.storage;

public class Ingredient {
	private Long id;
	private String name;
	private double quantity_fridge; /*Neviem ci moze byt double*/ 
	private Allergie alergie;
	private Measure measure;

	
	
	public Ingredient() {
		
	}

	public Ingredient(Long id, String name, Allergie alergie, Measure measure) {
		this.id = id;
		this.name = name;
		this.alergie = alergie;
		this.measure = measure;
		
	}

	public Ingredient(Long id, String name, double quantity_fridge, Allergie alergie, Measure measure) {
		this.id = id;
		this.name = name;
		this.quantity_fridge = quantity_fridge;
		this.alergie = alergie;
		this.measure = measure;
	
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getQuantity_fridge() {
		return quantity_fridge;
	}

	public void setQuantity_fridge(double quantity_fridge) {
		this.quantity_fridge = quantity_fridge;
	}

	public Allergie getAlergie() {
		return alergie;
	}

	public void setAlergie(Allergie alergie) {
		this.alergie = alergie;
	}
	
	public Measure getMeasure() {
		return measure;
	}

	public void setMeasure(Measure measure) {
		this.measure = measure;
	}

	@Override
	public String toString() {
		return "Ingredient [id=" + id + ", name=" + name + ", quantity_fridge=" + quantity_fridge + ", alergie="
				+ alergie + ", measure=" + measure + "]";
	}

	
	
	
	
}
