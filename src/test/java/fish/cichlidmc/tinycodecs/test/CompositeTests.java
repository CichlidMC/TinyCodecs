package fish.cichlidmc.tinycodecs.test;

import fish.cichlidmc.tinycodecs.test.types.TestRecord;
import fish.cichlidmc.tinyjson.value.JsonValue;
import fish.cichlidmc.tinyjson.value.composite.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompositeTests {
	@Test
	public void testRecord() {
		TestRecord record = new TestRecord(4, "test", false);
		JsonValue json = TestRecord.CODEC.encode(record).getOrThrow();

		JsonObject expected = new JsonObject()
				.put("i", 4)
				.put("s", "test")
				.put("gerald", false);

		assertEquals(expected, json);

		TestRecord decoded = TestRecord.CODEC.decode(json).getOrThrow();
		assertEquals(record, decoded);
	}

	@Test
	public void optionalFieldMissing() {
		TestRecord expected = new TestRecord(1, "h!", true);

		JsonObject json = new JsonObject()
				.put("i", 1)
				.put("s", "h!");

		TestRecord decoded = TestRecord.CODEC.decode(json).getOrThrow();
		assertEquals(expected, decoded);
	}

}
