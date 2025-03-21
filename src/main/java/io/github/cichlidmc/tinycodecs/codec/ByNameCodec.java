package io.github.cichlidmc.tinycodecs.codec;

import io.github.cichlidmc.tinycodecs.Codec;
import io.github.cichlidmc.tinycodecs.DecodeResult;
import io.github.cichlidmc.tinyjson.value.JsonValue;
import io.github.cichlidmc.tinyjson.value.primitive.JsonString;

import java.util.function.Function;

public final class ByNameCodec<T> implements Codec<T> {
	private final Function<T, String> nameGetter;
	private final Function<String, T> byName;

	public ByNameCodec(Function<T, String> nameGetter, Function<String, T> byName) {
		this.nameGetter = nameGetter;
		this.byName = byName;
	}

	@Override
	public DecodeResult<T> decode(JsonValue value) {
		if (!(value instanceof JsonString)) {
			return DecodeResult.error("Name is not a String");
		}

		String name = value.asString().value();
		T named = this.byName.apply(name);

		return named != null ? DecodeResult.success(named) : DecodeResult.error("No entry named " + name);
	}

	@Override
	public JsonValue encode(T value) {
		String name = this.nameGetter.apply(value);
		return new JsonString(name);
	}
}
