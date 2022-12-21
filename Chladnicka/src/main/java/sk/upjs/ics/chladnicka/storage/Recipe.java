package sk.upjs.ics.chladnicka.storage;

import java.util.List;
import java.util.Objects;

public class Recipe {

	private Long id;
	private String recipe_name;
	private double calorific;
	private String description;
	private Diet diet;
	private List<Ingredient> ingredient;

	public Recipe() {

	}

	public Recipe(Long id, String recipe_name, double calorific, String description, Diet diet) {
		this.id = id;
		this.recipe_name = recipe_name;
		this.calorific = calorific;
		this.description = description;
		this.diet = diet;
	}

	public Recipe(Long id, String recipe_name, double calorific, String description, Diet diet,
			List<Ingredient> ingredient) {
		this.id = id;
		this.recipe_name = recipe_name;
		this.calorific = calorific;
		this.description = description;
		this.diet = diet;
		this.ingredient = ingredient;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRecipe_name() {
		return recipe_name;
	}

	public void setRecipe_name(String recipe_name) {
		this.recipe_name = recipe_name;
	}

	public double getCalorific() {
		return calorific;
	}

	public void setCalorific(double calorific) {
		this.calorific = calorific;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Diet getDiet() {
		return diet;
	}

	public void setDiet(Diet diet) {
		this.diet = diet;
	}

	public List<Ingredient> getIngredient() {
		return ingredient;
	}

	public void setIngredient(List<Ingredient> ingredient) {
		this.ingredient = ingredient;
	}

	@Override
	public String toString() {
		return "(" + id + "), " + recipe_name + ", calorific value: " + calorific;
	}

	@Override
	public int hashCode() {
		return Objects.hash(calorific, description, diet, id, ingredient, recipe_name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recipe other = (Recipe) obj;
		return Double.doubleToLongBits(calorific) == Double.doubleToLongBits(other.calorific)
				&& Objects.equals(description, other.description) && Objects.equals(diet, other.diet)
				&& Objects.equals(id, other.id) && Objects.equals(ingredient, other.ingredient)
				&& Objects.equals(recipe_name, other.recipe_name);
	}

}
