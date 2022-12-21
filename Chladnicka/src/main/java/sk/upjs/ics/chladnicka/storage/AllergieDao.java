package sk.upjs.ics.chladnicka.storage;

import java.util.List;

public interface AllergieDao {
	List<Allergie> getAll();

	Allergie getByID(long id);
}
