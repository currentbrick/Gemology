package com.currentbrick.gemology.blockentity;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.container.IncubatorContainer;
import com.currentbrick.gemology.entity.EntityGem;
import com.currentbrick.gemology.entity.gem.GemDefinition;
import com.currentbrick.gemology.entity.gem.GemInstanceData;
import com.currentbrick.gemology.init.ModBlockEntities;
import com.currentbrick.gemology.init.ModRecipeTypes;
import com.currentbrick.gemology.recipe.CruxRequirement;
import com.currentbrick.gemology.recipe.IncubationInput;
import com.currentbrick.gemology.recipe.IncubationRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.extensions.IMenuProviderExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public class IncubatorBE extends BlockEntity implements Container, MenuProvider, IMenuProviderExtension {

    private static final int INVENTORY_SIZE = 13;

    private static final int CRUX_START = 0;
    private static final int CRUX_END = 7;
    private static final int GEM_BASE_SLOT = 8;
    private static final int ESSENCE_1_SLOT = 9;
    private static final int ESSENCE_2_SLOT = 10;
    private static final int CHROMA_SLOT = 11;
    public static final int OUTPUT_SLOT = 12;

    private boolean incubating = false;
    private int incubationProgress = 0;
    private int incubationTime = 0;

    private Identifier currentRecipeId = null;

    private final NonNullList<ItemStack> inventory = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);


    public IncubatorBE(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.INCUBATOR_BE.get(), worldPosition, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, IncubatorBE be) {
        if (level.isClientSide()) {
            return;
        }
        be.tickIncubation();
    }

    private void tickIncubation() {
        if (!incubating) {
            return;
        }

        if (incubationTime <= 0) {
            completeIncubation();
            return;
        }

        if (incubationProgress < incubationTime) {
            incubationProgress++;
            setChanged();
        }

        if (incubationProgress >= incubationTime) {
            completeIncubation();
        }
    }


    public void startIncubation() {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        if (incubating) {
            return;
        }

        Optional<RecipeHolder<IncubationRecipe>> matchingRecipe = getMatchingRecipe();

        if (matchingRecipe.isEmpty()) {
            return;
        }

        GemDefinition definition = Gemology.GEM_DEFINITION_MANAGER.get(matchingRecipe.get().value().gem());

        if (!isGemAvailable(definition, serverLevel)) {
            return;
        }

        System.out.println(matchingRecipe.get());

        RecipeHolder<IncubationRecipe> recipeHolder = matchingRecipe.get();
        IncubationRecipe recipe = recipeHolder.value();

        currentRecipeId = recipeHolder.id().identifier();

        incubationProgress = 0;
        incubationTime = recipe.incubationTime();
        incubating = true;

        setChanged();
    }

    public static boolean isGemAvailable(GemDefinition definition, ServerLevel level) {
        LocalDate date = LocalDate.now();
        return definition.isAvailable(date);
    }


    private void consumeInputs() {
        removeItem(GEM_BASE_SLOT, 1);
        removeItem(ESSENCE_1_SLOT, 1);
        removeItem(ESSENCE_2_SLOT, 1);
        removeItem(CHROMA_SLOT, 1);

        for (int slot = CRUX_START; slot <= CRUX_END; slot++) {
            removeItem(slot, 1);
        }
    }

    private void completeIncubation() {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        if (!getItem(OUTPUT_SLOT).isEmpty()) {
            return;
        }

        if (currentRecipeId == null) {
            return;
        }

        ResourceKey<Recipe<?>> recipeKey =
                ResourceKey.create(
                        Registries.RECIPE,
                        currentRecipeId
                );

        Optional<RecipeHolder<IncubationRecipe>> recipeHolder =
                serverLevel.recipeAccess()
                        .byKey(recipeKey)
                        .filter(holder -> holder.value() instanceof IncubationRecipe)
                        .map(holder -> (RecipeHolder<IncubationRecipe>) holder);

        if (recipeHolder.isEmpty()) {
            return;
        }

        IncubationRecipe recipe = recipeHolder.get().value();

        ItemStack result = createGemItem(recipe);

        if (result.isEmpty()) {
            return;
        }

        consumeInputs();

        setItem(OUTPUT_SLOT, result);

        incubating = false;
        incubationProgress = 0;
        incubationTime = 0;
        currentRecipeId = null;

        setChanged();
    }


    private ItemStack createGemItem(IncubationRecipe recipe) {
        Identifier gemId = recipe.gem();

        ItemStack chromaStack = getItem(CHROMA_SLOT);

        int variant = recipe.getVariantId(chromaStack);

        if (variant == -1) {

            return ItemStack.EMPTY;

        }

        GemInstanceData instance = new GemInstanceData(UUID.randomUUID(), calculateQuality(recipe), variant);

        return EntityGem.createGemItem(gemId, instance.getInstanceId(), instance.getQuality(), instance.getVariant());
    }


    @Override
    public int getContainerSize() {
        return INVENTORY_SIZE;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : inventory) {
            if (!stack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack result = ContainerHelper.removeItem(inventory, slot, amount);

        if (!result.isEmpty()) {
            setChanged();
        }

        return result;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(inventory, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        inventory.set(slot, stack);

        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }

        setChanged();
    }

    @Override
    public void setChanged() {
        super.setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        inventory.clear();
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, inventory);
        output.putBoolean("Incubating", incubating);
        output.putInt("IncubationProgress", incubationProgress);
        output.putInt("IncubationTime", incubationTime);
        if (currentRecipeId != null) {
            output.putString("CurrentRecipe", currentRecipeId.toString());
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        ContainerHelper.loadAllItems(input, inventory);
        incubating = input.getBooleanOr("Incubating", false);
        incubationProgress = input.getIntOr("IncubationProgress", 0);
        incubationTime = input.getIntOr("IncubationTime", 0);
        String recipeId = input.getString("CurrentRecipe").orElse(null);

        currentRecipeId = recipeId != null
                ? Identifier.tryParse(recipeId)
                : null;
    }

    public int getIncubationProgress() {
        return incubationProgress;
    }

    public int getIncubationTime() {
        return incubationTime;
    }

    public boolean isIncubating() {
        return incubating;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.gemology.incubator");
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        ContainerData data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> getIncubationProgress();
                    case 1 -> getIncubationTime();
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
            }

            @Override
            public int getCount() {
                return 2;
            }
        };

        return new IncubatorContainer(
                containerId,
                playerInventory,
                this,
                data
        );
    }

    @Override
    public void writeClientSideData(AbstractContainerMenu menu, RegistryFriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.worldPosition);
    }

    private IncubationInput createRecipeInput() {
        return new IncubationInput(
                getItem(GEM_BASE_SLOT),
                getItem(ESSENCE_1_SLOT),
                getItem(ESSENCE_2_SLOT),
                getItem(CHROMA_SLOT),
                getItem(0),
                getItem(1),
                getItem(2),
                getItem(3),
                getItem(4),
                getItem(5),
                getItem(6),
                getItem(7)
        );
    }

    public Optional<RecipeHolder<IncubationRecipe>> getMatchingRecipe() {
        if (!(level instanceof ServerLevel serverLevel)) {
            return Optional.empty();
        }

        IncubationInput input = createRecipeInput();

        return serverLevel.recipeAccess().getRecipeFor(
                ModRecipeTypes.INCUBATION.get(),
                input,
                serverLevel
        );
    }


    public float calculateQuality(IncubationRecipe recipe) {
        RandomSource random = RandomSource.create();

        float totalQuality = 0.0F;
        int successfulCruxes = 0;

        for (int slot = CRUX_START; slot <= CRUX_END; slot++) {
            ItemStack crux = getItem(slot);

            if (crux.isEmpty()) {
                continue;
            }

            for (CruxRequirement requirement : recipe.cruxes()) {
                if (!requirement.ingredient().test(crux)) {
                    continue;
                }

                if (random.nextFloat() <= requirement.chance()) {
                    totalQuality += requirement.quality();
                    successfulCruxes++;
                }

                break;
            }
        }

        if (successfulCruxes == 0) {
            return 0.0F;
        }

        return totalQuality;
    }
}
