package jotato.quantumflux.nei;

import java.awt.Rectangle;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import jotato.quantumflux.machine.fabricator.GUIItemFabricator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemFabricatorNEIHandler extends TemplateRecipeHandler {

	private final static String recipeID="quantumflux:itemFabricator";

	
	@Override
	public String getRecipeName() {
		return StatCollector.translateToLocal("tile.itemFabricator.name");
	}

	@Override
	public String getGuiTexture() {
		return "quantumflux:textures/gui/molecularInfuser.png";
	}

	@Override
	public int recipiesPerPage() {
		return 1;
	}
	
	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(55, 53, 15, 27), recipeID));
		transferRects.add(new RecipeTransferRect(new Rectangle(96, 53, 15, 27), recipeID));
	}
	
	@Override
	public Class<? extends GuiContainer> getGuiClass() {
		return GUIItemFabricator.class;
	}

	public Set<Entry<List<PositionedStack>, PositionedStack>> getRecipes(){
		return NEIQuantumFluxConfig.getIitemFabricatorRecipes();
	}
	
    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        for (Entry<List<PositionedStack>, PositionedStack> recipe : this.getRecipes())
        {
            if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getValue().item, result))
            {
                this.arecipes.add(new CachedItemFabricatorRecipe(recipe));
            }
        }
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals(recipeID))
        {
            for (Entry<List<PositionedStack>, PositionedStack> irecipe : this.getRecipes())
            {
                this.arecipes.add(new CachedItemFabricatorRecipe(irecipe));
            }
        }
        else
        {
            super.loadCraftingRecipes(outputId, results);
        }
    }
    
    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        for (Entry<List<PositionedStack>, PositionedStack> recipe : this.getRecipes())
        {
            for (PositionedStack stack : recipe.getKey())
            {
                if (NEIServerUtils.areStacksSameTypeCrafting(ingredient, stack.item))
                {
                    this.arecipes.add(new CachedItemFabricatorRecipe(recipe));
                    break;
                }
            }
        }
    }
   
    
    @Override
    public void drawBackground(int recipe) {
    	GL11.glColor4f(1, 1, 1, 1);
    	GuiDraw.changeTexture(getGuiTexture());
    	GuiDraw.drawTexturedModalRect(0, 14, 5, 4, 166, 78);
    }
    
    @Override
    public void drawExtras(int recipe) {
    	drawProgressBar(55, 53, 16, 166, 56, 27, 48, 1);
    	drawProgressBar(149, 15, 0, 166, 12, 76, 48 * (50000 / 5600), 7);
    }
   
    
	public class CachedItemFabricatorRecipe extends TemplateRecipeHandler.CachedRecipe {
		public List<PositionedStack> input;
		public PositionedStack output;

		@Override
		public List<PositionedStack> getIngredients() {
			return this.input;
		}

		@Override
		public PositionedStack getResult() {
			return this.output;
		}

		public CachedItemFabricatorRecipe(List<PositionedStack> in, PositionedStack out) {
			super();
			this.input = in;
			this.output = out;
		}

		public CachedItemFabricatorRecipe(Entry<List<PositionedStack>, PositionedStack> recipe) {
			this(recipe.getKey(), recipe.getValue());
		}
	}

}
