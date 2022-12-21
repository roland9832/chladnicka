package sk.upjs.ics.chladnicka.storage;

import java.util.Objects;

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
		return unit;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, unit);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Measure other = (Measure) obj;
		return Objects.equals(id, other.id) && Objects.equals(unit, other.unit);
	}

}
