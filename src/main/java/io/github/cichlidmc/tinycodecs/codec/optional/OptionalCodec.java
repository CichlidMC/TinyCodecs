package io.github.cichlidmc.tinycodecs.codec.optional;

import io.github.cichlidmc.tinycodecs.Codec;
import io.github.cichlidmc.tinycodecs.DecodeResult;
import io.github.cichlidmc.tinyjson.value.JsonValue;
import io.github.cichlidmc.tinyjson.value.primitive.JsonNull;

import java.util.Optional;

public final class OptionalCodec<T> implements Codec<Optional<T>> {
	private final Codec<T> wrapped;

	public OptionalCodec(Codec<T> wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public DecodeResult<Optional<T>> decode(JsonValue value) {
		if (value instanceof JsonNull) {
			return DecodeResult.success(Optional.empty());
		} else {
			return this.wrapped.decode(value).map(Optional::of);
		}
	}

	@Override
	public JsonValue encode(Optional<T> value) {
		return value.map(this.wrapped::encode).orElseGet(JsonNull::new);
	}
}
