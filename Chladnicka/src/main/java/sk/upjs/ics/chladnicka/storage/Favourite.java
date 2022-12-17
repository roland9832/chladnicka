package sk.upjs.ics.chladnicka.storage;

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
	
	

	public Favourite(Long id, Recipe recipe, int hodnotenie) {
		this.id = id;
		this.recipe = recipe;
		this.hodnotenie = hodnotenie;
	}

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
		return "Favourite [id=" + id + ", recipe=" + recipe + ", hodnotenie=" + hodnotenie + "]";
	}

	
	
	
}
