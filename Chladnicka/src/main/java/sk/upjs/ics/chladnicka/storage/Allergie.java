package sk.upjs.ics.chladnicka.storage;

public class Allergie {

	private Long id;
	private String category;
	private String information;
	
	public Allergie() {
		
	}
	
	public Allergie(Long id, String category, String information) {
		this.id = id;
		this.category = category;
		this.information = information;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	@Override
	public String toString() {
		return "Allergie [id=" + id + ", category=" + category + ", information=" + information + "]";
	}
	
	
	
	
}
