package sk.upjs.ics.chladnicka.storage;

import java.util.List;

public interface DietDao {
	List<Diet> getAll();
	Diet getByID(long id);
	Diet getByName(String name);
}
