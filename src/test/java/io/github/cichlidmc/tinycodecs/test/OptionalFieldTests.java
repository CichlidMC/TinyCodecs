package io.github.cichlidmc.tinycodecs.test;

import io.github.cichlidmc.tinycodecs.test.types.TestRecord;
import io.github.cichlidmc.tinyjson.value.composite.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OptionalFieldTests {
	@Test
	public void dontEncodeDefault() {
		TestRecord record = new TestRecord(1, "test", true);
		JsonObject expected = new JsonObject()
				.put("i", 1)
				.put("s", "test");

		assertEquals(expected, TestRecord.CODEC.encode(record).getOrThrow());
	}
}
