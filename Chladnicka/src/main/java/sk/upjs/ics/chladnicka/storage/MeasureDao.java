package sk.upjs.ics.chladnicka.storage;

import java.util.List;

public interface MeasureDao {
	List<Measure> getAll();

	Measure getByID(long id);
}
