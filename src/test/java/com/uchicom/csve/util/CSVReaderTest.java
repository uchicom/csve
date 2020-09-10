// (c) 2017 uchicom
package com.uchicom.csve.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

/**
 * @author uchicom: Shigeki Uchiyama
 *
 */
public class CSVReaderTest {

	/**
	 * 
	 */
	@Test
	public void getNextCsvLine1() {
		String string = "1,2,3,4";
		ByteArrayInputStream bais = new ByteArrayInputStream(string.getBytes());
		try (CSVReader reader = new CSVReader(bais, StandardCharsets.UTF_8);) {

			String[] result = reader.getNextCsvLine(4, true);
			assertEquals(4, result.length);
			assertEquals("1", result[0]);
			assertEquals("2", result[1]);
			assertEquals("3", result[2]);
			assertEquals("4", result[3]);
			assertNull(reader.getNextCsvLine(4, true));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void getNextCsvLine2() {
		String string = "あ,い,う,え";
		ByteArrayInputStream bais = new ByteArrayInputStream(string.getBytes());
		try (CSVReader reader = new CSVReader(bais, StandardCharsets.UTF_8);) {

			String[] result = reader.getNextCsvLine(4, true);
			assertEquals(4, result.length);
			assertEquals("あ", result[0]);
			assertEquals("い", result[1]);
			assertEquals("う", result[2]);
			assertEquals("え", result[3]);
			assertNull(reader.getNextCsvLine(4, true));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void getNextCsvLine3() {
		String string = "あ,い,う,え\n1,2,3,4";
		ByteArrayInputStream bais = new ByteArrayInputStream(string.getBytes());
		try (CSVReader reader = new CSVReader(bais, StandardCharsets.UTF_8);) {

			String[] result = reader.getNextCsvLine(4, true);
			assertEquals(4, result.length);
			assertEquals("あ", result[0]);
			assertEquals("い", result[1]);
			assertEquals("う", result[2]);
			assertEquals("え", result[3]);
			result = reader.getNextCsvLine(4, true);
			assertEquals(4, result.length);
			assertEquals("1", result[0]);
			assertEquals("2", result[1]);
			assertEquals("3", result[2]);
			assertEquals("4", result[3]);
			assertNull(reader.getNextCsvLine(4, true));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void getNextCsvLine4() {
		String string = ",,,";
		ByteArrayInputStream bais = new ByteArrayInputStream(string.getBytes());
		try (CSVReader reader = new CSVReader(bais, StandardCharsets.UTF_8);) {

			String[] result = reader.getNextCsvLine(4, true);
			assertEquals(4, result.length);
			assertEquals("", result[0]);
			assertEquals("", result[1]);
			assertEquals("", result[2]);
			assertEquals("", result[3]);
			assertNull(reader.getNextCsvLine(4, true));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void getNextCsvLine5() {
		String string = "\"a\",\",\",\"\n\",a\"\\\\\"b";
		ByteArrayInputStream bais = new ByteArrayInputStream(string.getBytes());
		try (CSVReader reader = new CSVReader(bais, StandardCharsets.UTF_8);) {

			String[] result = reader.getNextCsvLine(4, true);
			assertEquals(4, result.length);
			assertEquals("a", result[0]);
			assertEquals(",", result[1]);
			assertEquals("\n", result[2]);
			assertEquals("a\"\\\\\"b", result[3]);
			assertNull(reader.getNextCsvLine(4, true));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void escape() {
		assertEquals("test", new String(CSVReader.escape("test".toCharArray(), '"', 0)));
		assertEquals("\"", new String(CSVReader.escape("\"\"".toCharArray(), '"', 1)));
		assertEquals("t\"st", new String(CSVReader.escape("t\"\"st".toCharArray(), '"', 1)));
		assertEquals("\"\"", new String(CSVReader.escape("\"\"\"\"".toCharArray(), '"', 2)));
	}
}
