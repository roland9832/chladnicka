package sk.upjs.ics.chladnicka.storage;

import static org.junit.Assert.assertEquals;
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

	private Measure globalMaesure;

	public MysqlMeasureDaoTest() {
		DaoFactory.INSTANCE.setTesting();
		measureDao = DaoFactory.INSTANCE.getMeasureDao();
	}

	@BeforeEach
	void setUp() throws Exception {
		globalMaesure = measureDao.getByID(measureDao.getAll().size()-1);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetAll() {
		List<Measure> measure = measureDao.getAll();
		assertTrue(measure.size() > 0);
		assertNotNull(measure.get(0).getId());
		assertNotNull(measure.get(0).getUnit());
	}

	@Test
	void testGetByID() {
		Measure fromDB = measureDao.getByID(measureDao.getAll().size()-1);
		assertEquals(globalMaesure.getId(), fromDB.getId());
		assertEquals(globalMaesure.getUnit(), fromDB.getUnit());
		assertEquals(globalMaesure.getClass(), fromDB.getClass());
		assertThrows(NoSuchElementException.class,()->measureDao.getByID(-1));
		};

}
