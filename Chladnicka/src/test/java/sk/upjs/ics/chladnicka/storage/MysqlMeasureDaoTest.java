package sk.upjs.ics.chladnicka.storage;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class MysqlMeasureDaoTest {
	private MeasureDao measureDao;

	public MysqlMeasureDaoTest() {
		DaoFactory.INSTANCE.setTesting();
		measureDao = DaoFactory.INSTANCE.getMeasureDao();
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testByID() {
		List<Measure> measure = measureDao.getAll();
		assertTrue(measure.size() > 0);
		assertNotNull(measure.get(0).getId());
		assertNotNull(measure.get(0).getUnit());
	}

	@Test
	void testGetByID() {
		assertThrows(NoSuchElementException.class, new Executable() {

			@Override
			public void execute() throws Throwable {
				measureDao.getByID(-7l);

			}
		});

	}

}
