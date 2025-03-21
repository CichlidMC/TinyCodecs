package io.github.cichlidmc.tinycodecs.codec;

import io.github.cichlidmc.tinycodecs.Codec;
import io.github.cichlidmc.tinycodecs.DecodeResult;
import io.github.cichlidmc.tinyjson.value.JsonValue;
import io.github.cichlidmc.tinyjson.value.composite.JsonArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ListCodec<T> implements Codec<List<T>> {
	private final Codec<T> elementCodec;

	public ListCodec(Codec<T> elementCodec) {
		this.elementCodec = elementCodec;
	}

	@Override
	public DecodeResult<List<T>> decode(JsonValue value) {
		if (!(value instanceof JsonArray)) {
			return DecodeResult.error("Not a list: " + value);
		}

		JsonArray array = value.asArray();
		if (array.size() == 0) {
			return DecodeResult.success(Collections.emptyList());
		} else if (array.size() == 1) {
			return this.elementCodec.decode(array.get(0)).map(Collections::singletonList);
		}

		List<T> decoded = new ArrayList<>();
		List<String> errors = new ArrayList<>();

		for (JsonValue element : array) {
			this.elementCodec.decode(element).ifPresentOrElse(
					decoded::add, errors::add
			);
		}

		if (errors.isEmpty()) {
			return DecodeResult.success(decoded);
		}

		StringBuilder fullError = new StringBuilder("Failed to decode list: ");
		errors.forEach(error -> fullError.append(error).append("; "));
		return DecodeResult.error(fullError.toString().trim());
	}

	@Override
	public JsonValue encode(List<T> value) {
		JsonArray array = new JsonArray();
		value.forEach(entry -> array.add(this.elementCodec.encode(entry)));
		return array;
	}
}
