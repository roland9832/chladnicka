package sk.upjs.ics.chladnicka.storage;

public class Measure {
	private Long id;
	private String unit;
	
	public Measure() {
		
	}
	
	public Measure(long id2, String unit) {
		this.id = id2;
		this.unit = unit;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public String toString() {
		return "Measure [id=" + id + ", unit=" + unit + "]";
	}
	
}
