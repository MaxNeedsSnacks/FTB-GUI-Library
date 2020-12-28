package com.feed_the_beast.mods.ftbguilibrary.utils;

import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

/**
 * Made by LatvianModder
 */
public class MathUtils
{
	public static final Random RAND = new Random();

	public static final float[] NORMALS_X = new float[] {0F, 0F, 0F, 0F, -1F, 1F};
	public static final float[] NORMALS_Y = new float[] {-1F, 1F, 0F, 0F, 0F, 0F};
	public static final float[] NORMALS_Z = new float[] {0F, 0F, -1F, 1F, 0F, 0F};

	public static final int[] ROTATION_X = {90, 270, 0, 0, 0, 0};
	public static final int[] ROTATION_Y = {0, 0, 180, 0, 90, 270};

	public static double sq(double value)
	{
		return value * value;
	}

	public static double sqrt(double value)
	{
		return value == 0D || value == 1D ? value : Math.sqrt(value);
	}

	public static double sqrt2sq(double x, double y)
	{
		return sqrt(sq(x) + sq(y));
	}

	public static double sqrt3sq(double x, double y, double z)
	{
		return sqrt(sq(x) + sq(y) + sq(z));
	}

	public static double distSq(double x1, double y1, double z1, double x2, double y2, double z2)
	{
		return (x1 == x2 && y1 == y2 && z1 == z2) ? 0D : (sq(x2 - x1) + sq(y2 - y1) + sq(z2 - z1));
	}

	public static double dist(double x1, double y1, double z1, double x2, double y2, double z2)
	{
		return sqrt(distSq(x1, y1, z1, x2, y2, z2));
	}

	public static double distSq(double x1, double y1, double x2, double y2)
	{
		return sq(x2 - x1) + sq(y2 - y1);
	}

	public static double dist(double x1, double y1, double x2, double y2)
	{
		return sqrt(distSq(x1, y1, x2, y2));
	}

	public static int chunk(int i)
	{
		return i >> 4;
	}

	public static int chunk(double d)
	{
		return chunk(Mth.floor(d));
	}

	public static boolean canParseInt(@Nullable String string)
	{
		if (string == null || string.isEmpty())
		{
			return false;
		}

		try
		{
			Integer.parseInt(string);
			return true;
		}
		catch (Exception ex)
		{
			return false;
		}
	}

	public static boolean canParseDouble(@Nullable String string)
	{
		if (string == null || string.isEmpty())
		{
			return false;
		}

		try
		{
			Double.parseDouble(string);
			return true;
		}
		catch (Exception ex)
		{
			return false;
		}
	}

	public static float lerp(float min, float max, float value)
	{
		return min + (max - min) * value;
	}

	public static double lerp(double min, double max, double value)
	{
		return min + (max - min) * value;
	}

	public static Vec3 lerp(double x1, double y1, double z1, double x2, double y2, double z2, double value)
	{
		return new Vec3(lerp(x1, x2, value), lerp(y1, y2, value), lerp(z1, z2, value));
	}

	public static Vec3 lerp(Vec3 v1, Vec3 v2, double value)
	{
		return lerp(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z, value);
	}

	public static double map(double min1, double max1, double min2, double max2, double value)
	{
		return lerp(min2, max2, (value - min1) / (max1 - min1));
	}

	public static double mod(double i, double n)
	{
		i = i % n;
		return i < 0 ? i + n : i;
	}

	public static int mod(int i, int n)
	{
		i = i % n;
		return i < 0 ? i + n : i;
	}

	private static final int CACHED_SPIRAL_POINTS_SIZE = 9 * 9;
	private static ChunkPos[] CACHED_SPIRAL_POINTS = null;

	public static ChunkPos getSpiralPoint(int index)
	{
		if (index < 0)
		{
			index = 0;
		}

		if (index < CACHED_SPIRAL_POINTS_SIZE)
		{
			if (CACHED_SPIRAL_POINTS == null)
			{
				CACHED_SPIRAL_POINTS = new ChunkPos[CACHED_SPIRAL_POINTS_SIZE];

				for (int i = 0; i < CACHED_SPIRAL_POINTS_SIZE; i++)
				{
					CACHED_SPIRAL_POINTS[i] = getSpiralPoint0(i);
				}
			}

			return CACHED_SPIRAL_POINTS[index];
		}

		return getSpiralPoint0(index);
	}

	public static ChunkPos getSpiralPoint0(int index)
	{
		int x = 0, z = 0, p = 1, ringIndex = 0;
		double sqrtceil = Math.ceil(Math.sqrt(index));
		int s = (int) (sqrtceil + ((sqrtceil % 2 + 1) % 2));

		if (s > 1)
		{
			ringIndex = index - (s - 2) * (s - 2);
			p = s * s - (s - 2) * (s - 2);
		}

		int ri = (ringIndex + s / 2) % p;

		if (s > 1)
		{
			x = ri < (p / 4) ? ri : (ri <= (p / 4 * 2 - 1) ? (p / 4) : (ri <= (p / 4 * 3) ? ((p / 4 * 3) - ri) : 0));
		}

		if (s > 1)
		{
			z = ri < (p / 4) ? 0 : (ri <= (p / 4 * 2 - 1) ? (ri - (p / 4)) : (ri <= (p / 4 * 3) ? (p / 4) : (p - ri)));
		}

		return new ChunkPos(x - s / 2, z - s / 2);
	}
}