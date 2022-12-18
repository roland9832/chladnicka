package sk.upjs.ics.chladnicka.storage;

import java.util.Objects;

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

	@Override
	public int hashCode() {
		return Objects.hash(category, id, information);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Allergie other = (Allergie) obj;
		return Objects.equals(category, other.category) && Objects.equals(id, other.id)
				&& Objects.equals(information, other.information);
	}
	
	
	
	
}
