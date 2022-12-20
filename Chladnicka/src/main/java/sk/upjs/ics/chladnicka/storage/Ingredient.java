package sk.upjs.ics.chladnicka.storage;

import java.util.Objects;

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

	public Ingredient(Long id, String name, double quantity_fridge, Measure measure) {
		this.id = id;
		this.name = name;
		this.quantity_fridge = quantity_fridge;
		this.measure = measure;
	}

	

	public Ingredient(String name, double quantity_fridge, Allergie alergie, Measure measure) {
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
		if(alergie.getId() == 13) {
			return "(" + id + ") " + name + ", " + quantity_fridge + measure.getUnit() +  ", Allergy: None";
		}else {
			return "(" + id + ") " + name + ", " + quantity_fridge + measure.getUnit() +  ", Allergy: "
					+ alergie.getCategory();
		}
		
	}

	@Override
	public int hashCode() {
		return Objects.hash(alergie, id, measure, name, quantity_fridge);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingredient other = (Ingredient) obj;
		return Objects.equals(alergie, other.alergie) && Objects.equals(id, other.id)
				&& Objects.equals(measure, other.measure) && Objects.equals(name, other.name)
				&& Double.doubleToLongBits(quantity_fridge) == Double.doubleToLongBits(other.quantity_fridge);
	}

	
	
	
	
}
