package io.github.cichlidmc.tinycodecs.map;

import io.github.cichlidmc.tinycodecs.Codec;
import io.github.cichlidmc.tinycodecs.CodecResult;
import io.github.cichlidmc.tinycodecs.codec.map.MapCodecAsCodec;
import io.github.cichlidmc.tinyjson.value.composite.JsonObject;

/**
 * Similar to a {@link Codec}, but operates specifically on maps, aka objects.
 */
public interface MapCodec<T> {
	/**
	 * Attempt to decode an object from fields on the given JSON object.
	 */
	CodecResult<T> decode(JsonObject json);

	/**
	 * Encode the given value onto the given JSON object.
	 * @return a result, containing the given JSON if successful
	 */
	CodecResult<JsonObject> encode(JsonObject json, T value);

	default Codec<T> asCodec() {
		return new MapCodecAsCodec<>(this);
	}
}
