package com.cedkilleur.cedendcake.integration;

import com.cedkilleur.cedendcake.proxy.CommonProxy;

import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class CedJEIIntegration implements IModPlugin {

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
	}

	@Override
	public void registerIngredients(IModIngredientRegistration registry) {
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
	}

	@Override
	public void register(IModRegistry registry) {
		//End Cake
		registry.addIngredientInfo(new ItemStack(CommonProxy.cedCake), ItemStack.class,
				I18n.format("Place the cake and fill it with %s. Right click to teleport to then End",
						Items.ENDER_EYE.getItemStackDisplayName(new ItemStack(Items.ENDER_EYE))));
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
	}

}
