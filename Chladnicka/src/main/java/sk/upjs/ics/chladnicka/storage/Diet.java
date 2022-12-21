package sk.upjs.ics.chladnicka.storage;

import java.util.Objects;

public class Diet {

	private Long id;
	private String name;

	public Diet() {

	}

	public Diet(long id2, String name) {
		this.id = id2;
		this.name = name;
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

	@Override
	public String toString() {
		return "(" + id + ") " + name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Diet other = (Diet) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}

}
