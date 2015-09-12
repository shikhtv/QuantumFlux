package jotato.quantumflux.nei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import codechicken.nei.PositionedStack;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import cofh.lib.inventory.ComparableItemStack;
import jotato.quantumflux.Reference;
import jotato.quantumflux.blocks.ModBlocks;
import jotato.quantumflux.items.ModItems;
import jotato.quantumflux.machine.fabricator.ContainerItemFabricator;
import jotato.quantumflux.machine.fabricator.ItemFabricatorRecipeManager;
import jotato.quantumflux.machine.fabricator.ItemFabricatorRecipeManager.InfuserRecipe;
import jotato.quantumflux.util.SimplePosition;
import net.minecraft.item.ItemStack;

public class NEIQuantumFluxConfig implements IConfigureNEI {
	private static HashMap<List<PositionedStack>, PositionedStack> itemFabricatorRecipes = new HashMap();
	private static ItemFabricatorNEIHandler itemFabricatorNEIHandler;

	public  NEIQuantumFluxConfig() {
		itemFabricatorNEIHandler = new ItemFabricatorNEIHandler();
	}
	
	@Override
	public String getName() {
		return Reference.MODNAME;
	}

	@Override
	public String getVersion() {
		return Reference.VERSION;
	}

	@Override
	public void loadConfig() {
		loadRecipes();
		API.registerRecipeHandler(itemFabricatorNEIHandler);
		API.registerUsageHandler(itemFabricatorNEIHandler);
		API.hideItem(new ItemStack(ModBlocks.creativeCluster));
		API.hideItem(new ItemStack(ModBlocks.quibitCluster_1));
		API.hideItem(new ItemStack(ModBlocks.quibitCluster_2));
		API.hideItem(new ItemStack(ModBlocks.quibitCluster_3));
		API.hideItem(new ItemStack(ModBlocks.quibitCluster_4));
		API.hideItem(new ItemStack(ModBlocks.quibitCluster_5));
		API.hideItem(new ItemStack(ModItems.manganese));
		API.hideItem(new ItemStack(ModItems.harmonicOscillator));
		API.hideItem(new ItemStack(ModItems.titaniumPlate));
		API.hideItem(new ItemStack(ModItems.goldCasing));
	}

	private void loadRecipes() {
		loadItemFabricator();
	}

	private void loadItemFabricator() {

		SimplePosition slot1 = ContainerItemFabricator.slot1;
		SimplePosition slot2 = ContainerItemFabricator.slot2;
		SimplePosition slotOut = ContainerItemFabricator.slotOut;

		for (Entry<List<ComparableItemStack>, InfuserRecipe> recipe : ItemFabricatorRecipeManager.getRecipes()
				.entrySet()) {
			List<PositionedStack> key = new ArrayList();
			InfuserRecipe iRecipe = recipe.getValue();
			// todo: I don't know why the GUI draws a little off.
			// Figure it out and remove the offset calc
			key.add(new PositionedStack(iRecipe.getFirstInput(), slot1.X - 5, slot1.Y + 10));
			key.add(new PositionedStack(iRecipe.getSecondInput(), slot2.X - 5, slot2.Y + 10));

			itemFabricatorRecipes.put(key, new PositionedStack(iRecipe.getResult(), slotOut.X - 5, slotOut.Y + 10));

		}
	}

	public static Set<Entry<List<PositionedStack>, PositionedStack>> getIitemFabricatorRecipes() {
		return itemFabricatorRecipes.entrySet();
	}

}
