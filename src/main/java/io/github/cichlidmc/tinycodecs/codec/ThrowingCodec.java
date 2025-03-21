package io.github.cichlidmc.tinycodecs.codec;

import io.github.cichlidmc.tinycodecs.Codec;
import io.github.cichlidmc.tinycodecs.DecodeResult;
import io.github.cichlidmc.tinyjson.JsonException;
import io.github.cichlidmc.tinyjson.value.JsonValue;

/**
 * A codec that can safely throw a JsonException while decoding.
 * Any thrown exception will be caught and wrapped in a result.
 */
public interface ThrowingCodec<T> extends Codec<T> {
	T decodeUnsafe(JsonValue value) throws JsonException;

	@Override
	default DecodeResult<T> decode(JsonValue value) {
		try {
			return DecodeResult.success(this.decodeUnsafe(value));
		} catch (JsonException e) {
			return DecodeResult.error(e.getMessage());
		}
	}
}
