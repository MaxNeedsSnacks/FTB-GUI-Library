package com.feed_the_beast.mods.ftbguilibrary.config.gui;

import com.feed_the_beast.mods.ftbguilibrary.config.ConfigCallback;
import com.feed_the_beast.mods.ftbguilibrary.config.ConfigFluid;
import com.feed_the_beast.mods.ftbguilibrary.icon.Color4I;
import com.feed_the_beast.mods.ftbguilibrary.icon.Icon;
import com.feed_the_beast.mods.ftbguilibrary.icon.ItemIcon;
import com.feed_the_beast.mods.ftbguilibrary.misc.GuiButtonListBase;
import com.feed_the_beast.mods.ftbguilibrary.utils.Key;
import com.feed_the_beast.mods.ftbguilibrary.utils.MouseButton;
import com.feed_the_beast.mods.ftbguilibrary.widget.Panel;
import com.feed_the_beast.mods.ftbguilibrary.widget.SimpleTextButton;
import me.shedaniel.architectury.ExpectPlatform;
import me.shedaniel.architectury.fluid.FluidStack;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

/**
 * @author LatvianModder
 */
public class GuiSelectFluid extends GuiButtonListBase
{
	private final ConfigFluid config;
	private final ConfigCallback callback;

	public GuiSelectFluid(ConfigFluid c, ConfigCallback cb)
	{
		setTitle(new TranslatableComponent("ftbguilibrary.select_fluid.gui"));
		setHasSearchBox(true);
		config = c;
		callback = cb;
	}

	@Override
	public void addButtons(Panel panel)
	{
		if (config.allowEmpty)
		{
			FluidStack fluidStack = FluidStack.create(Fluids.EMPTY, FluidStack.bucketAmount());

			panel.add(new SimpleTextButton(panel, fluidStack.getName(), ItemIcon.getItemIcon(Items.BUCKET))
			{
				@Override
				public void onClicked(MouseButton button)
				{
					playClickSound();
					config.setCurrentValue(Fluids.EMPTY);
					callback.save(true);
				}

				@Override
				public Object getIngredientUnderMouse()
				{
					return FluidStack.create(Fluids.EMPTY, FluidStack.bucketAmount());
				}
			});
		}

		for (Fluid fluid : Registry.FLUID)
		{
			if (fluid == Fluids.EMPTY || fluid.defaultFluidState().isSource())
			{
				continue;
			}

			FluidStack fluidStack = FluidStack.create(fluid, FluidStack.bucketAmount());

			panel.add(new SimpleTextButton(panel, fluidStack.getName(), Icon.getIcon(getStillTexture(fluidStack)).withTint(Color4I.rgb(getColor(fluidStack))))
			{
				@Override
				public void onClicked(MouseButton button)
				{
					playClickSound();
					config.setCurrentValue(fluid);
					callback.save(true);
				}

				@Override
				public Object getIngredientUnderMouse()
				{
					return FluidStack.create(fluid, FluidStack.bucketAmount());
				}
			});
		}
	}

	@ExpectPlatform
	private static ResourceLocation getStillTexture(FluidStack stack)
	{
		throw new AssertionError();
	}

	@ExpectPlatform
	private static int getColor(FluidStack stack)
	{
		throw new AssertionError();
	}

	@Override
	public boolean onClosedByKey(Key key)
	{
		if (super.onClosedByKey(key))
		{
			callback.save(false);
			return false;
		}

		return false;
	}
}