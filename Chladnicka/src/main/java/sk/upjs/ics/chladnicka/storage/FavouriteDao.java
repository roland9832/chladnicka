package sk.upjs.ics.chladnicka.storage;

import java.util.List;

public interface FavouriteDao {
	List<Favourite> getAll();

	Favourite getByRecipe(Recipe recipe);

	void save(Favourite favourite);

	boolean delete(Recipe recipe) throws EntityUndeletableException;
}
