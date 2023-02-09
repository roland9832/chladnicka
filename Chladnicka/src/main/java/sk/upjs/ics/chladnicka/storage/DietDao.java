package sk.upjs.ics.chladnicka.storage;

import java.util.List;

public interface DietDao {
	List<Diet> getAll();

	Diet getByID(long id);
	
	Diet save(Diet diet);

//	Diet getByName(String name);

	boolean delete(long id) throws EntityUndeletableException;
}
