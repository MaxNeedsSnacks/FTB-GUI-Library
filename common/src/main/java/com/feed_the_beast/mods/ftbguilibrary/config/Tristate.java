package com.feed_the_beast.mods.ftbguilibrary.config;

import com.feed_the_beast.mods.ftbguilibrary.icon.Color4I;
import com.feed_the_beast.mods.ftbguilibrary.icon.Icon;
import com.feed_the_beast.mods.ftbguilibrary.widget.GuiIcons;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;

/**
 * @author LatvianModder
 */
public enum Tristate
{
	FALSE("false", "false", InteractionResult.FAIL, Color4I.rgb(0xD52834), 1, GuiIcons.ACCEPT_GRAY),
	TRUE("true", "true", InteractionResult.SUCCESS, Color4I.rgb(0x33AA33), 0, GuiIcons.ACCEPT),
	DEFAULT("default", "Default", InteractionResult.PASS, Color4I.rgb(0x0094FF), 2, GuiIcons.SETTINGS);

	public static final Tristate[] VALUES = values();
	public static final NameMap<Tristate> NAME_MAP = NameMap.of(DEFAULT, VALUES).id(v -> v.name).name(v -> new TextComponent(v.displayName)).color(v -> v.color).icon(v -> v.icon).create();

	public static Tristate read(CompoundTag nbt, String key)
	{
		return nbt.contains(key) ? nbt.getBoolean(key) ? TRUE : FALSE : DEFAULT;
	}

	public static Tristate read(FriendlyByteBuf buffer)
	{
		return VALUES[buffer.readUnsignedByte()];
	}

	public final String name;
	public final String displayName;
	public final InteractionResult result;
	public final Color4I color;
	private final int opposite;
	public final Icon icon;

	Tristate(String n, String dn, InteractionResult r, Color4I c, int o, Icon i)
	{
		name = n;
		displayName = dn;
		result = r;
		color = c;
		opposite = o;
		icon = i;
	}

	public boolean isTrue()
	{
		return this == TRUE;
	}

	public boolean isFalse()
	{
		return this == FALSE;
	}

	public boolean isDefault()
	{
		return this == DEFAULT;
	}

	public boolean get(boolean def)
	{
		return isDefault() ? def : isTrue();
	}

	public Tristate getOpposite()
	{
		return NAME_MAP.get(opposite);
	}

	public String toString()
	{
		return name;
	}

	public void write(CompoundTag nbt, String key)
	{
		if (!isDefault())
		{
			nbt.putBoolean(key, isTrue());
		}
	}

	public void write(FriendlyByteBuf buffer)
	{
		buffer.writeByte(ordinal());
	}
}