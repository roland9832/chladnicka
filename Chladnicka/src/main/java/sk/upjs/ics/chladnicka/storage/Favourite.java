package sk.upjs.ics.chladnicka.storage;

import java.util.Objects;

public class Favourite {
	private Long id;
	private Recipe recipe;
	private int hodnotenie;

	public Favourite() {

	}

	public Favourite(Long id, int hodnotenie) {
		this.id = id;
		this.hodnotenie = hodnotenie;
	}

	public Favourite(Recipe recipe, int hodnotenie) {
		this.id = recipe.getId();
		this.recipe = recipe;
		this.hodnotenie = hodnotenie;
	}

//	public Favourite(Long id, Recipe recipe, int hodnotenie) {
//		this.id = id;
//		this.recipe = recipe;
//		this.hodnotenie = hodnotenie;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public int getHodnotenie() {
		return hodnotenie;
	}

	public void setHodnotenie(int hodnotenie) {
		this.hodnotenie = hodnotenie;
	}

	@Override
	public String toString() {
		return "(id) " + id + ", Recipe name: " + recipe.getRecipe_name() + ", hodnotenie: " + hodnotenie;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, recipe);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Favourite other = (Favourite) obj;
		return Objects.equals(id, other.id) && Objects.equals(recipe, other.recipe);
	}

}
