package io.github.cichlidmc.tinycodecs.codec.map;

import io.github.cichlidmc.tinycodecs.DecodeResult;
import io.github.cichlidmc.tinycodecs.MapCodec;
import io.github.cichlidmc.tinyjson.JsonException;
import io.github.cichlidmc.tinyjson.value.composite.JsonObject;

/**
 * A MapCodec that can safely throw a JsonException while decoding.
 * Any thrown exception will be caught and wrapped in a result.
 */
public interface ThrowingMapCodec<T> extends MapCodec<T> {
	T decodeUnsafe(JsonObject json) throws JsonException;

	@Override
	default DecodeResult<T> decode(JsonObject json) {
		try {
			return DecodeResult.success(this.decodeUnsafe(json));
		} catch (JsonException e) {
			return DecodeResult.error(e.getMessage());
		}
	}
}
