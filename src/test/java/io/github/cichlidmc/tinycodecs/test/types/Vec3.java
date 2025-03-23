package io.github.cichlidmc.tinycodecs.test.types;

import io.github.cichlidmc.tinycodecs.Codec;
import io.github.cichlidmc.tinycodecs.Codecs;
import io.github.cichlidmc.tinycodecs.DecodeResult;
import io.github.cichlidmc.tinycodecs.util.Either;

import java.util.Arrays;

public final class Vec3 {
	public static final Codec<Vec3> INT_ARRAY_CODEC = Codecs.INT.listOf().flatXmap(
			list -> list.size() != 3 ? DecodeResult.error("Wrong size") : DecodeResult.success(new Vec3(list.get(0), list.get(1), list.get(2))),
			vec -> Arrays.asList(vec.x, vec.y, vec.z)
	);
	public static final Codec<Vec3> STRING_CODEC = Codecs.STRING.flatXmap(
			string -> {
				String[] split = string.split(",");
				if (split.length != 3) {
					return DecodeResult.error("Wrong size");
				}

				int[] ints = new int[3];
				for (int i = 0; i < split.length; i++) {
					try {
						ints[i] = Integer.parseInt(split[i]);
					} catch (NumberFormatException e) {
						return DecodeResult.error("not an int");
					}
				}

				return DecodeResult.success(new Vec3(ints[0], ints[1], ints[2]));
			},
			vec -> vec.x + "," + vec.y + ',' + vec.z
	);
	public static final Codec<Vec3> STRING_OR_INT_ARRAY_CODEC = STRING_CODEC.xor(INT_ARRAY_CODEC).xmap(Either::merge, Either::left);
	public static final Codec<Vec3> ZERO_UNIT_CODEC = Codecs.unit(new Vec3(0, 0, 0));

	public final int x;
	public final int y;
	public final int z;

	public Vec3(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
