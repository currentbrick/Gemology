package com.currentbrick.gemology.entity.gem;

import com.currentbrick.gemology.Gemology;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.io.IOException;
import java.io.Reader;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GemDefinitionLoader extends SimplePreparableReloadListener<Map<Identifier, GemDefinition>> {

    private static final Gson GSON = new Gson();

    private final GemDefinitionManager manager;

    public GemDefinitionLoader(GemDefinitionManager manager) {
        this.manager = manager;
    }

    @Override
    protected Map<Identifier, GemDefinition> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        Map<Identifier, GemDefinition> definitions = new HashMap<>();

        for (Map.Entry<Identifier, Resource> entry : resourceManager.listResources("gems", path -> path.getPath().endsWith(".json") && !path.getPath().startsWith("gems/abilities/")).entrySet()) {
            Identifier resourceId = entry.getKey();
            Resource resource = entry.getValue();

            String path = resourceId.getPath();

            String gemName = path.substring("gems/".length(), path.length() - ".json".length());

            Identifier gemId = Identifier.fromNamespaceAndPath(resourceId.getNamespace(), gemName);

            try (Reader reader = resource.openAsReader()) {
                JsonObject json = GSON.fromJson(reader, JsonObject.class);

                JsonObject stats = json.getAsJsonObject("stats");
                JsonObject dimensions = json.getAsJsonObject("dimensions");
                JsonArray abilitiesJson = json.getAsJsonArray("abilities");
                JsonArray variantsJson = json.getAsJsonArray("variants");
                JsonObject visualVariants = json.getAsJsonObject("visual_variants");

                Identifier parentGem = null;

                if (json.has("parent_gem")
                        && !json.get("parent_gem").isJsonNull()
                        && !json.get("parent_gem").getAsString().isBlank()) {

                    parentGem = Identifier.parse(
                            json.get("parent_gem").getAsString()
                    );
                }

                List<Identifier> abilities = new ArrayList<>();
                List<GemVariant> variants = new ArrayList<>();

                for (JsonElement element : abilitiesJson) {
                    abilities.add(Identifier.parse(element.getAsString()));
                }

                if (json.has("variants")) {
                    for (JsonElement element : variantsJson) {
                        JsonObject variantJson = element.getAsJsonObject();

                        int id = variantJson.get("id").getAsInt();
                        String name = variantJson.get("name").getAsString();

                        Identifier chromaId = null;

                        if (variantJson.has("chroma")
                                && !variantJson.get("chroma").isJsonNull()
                                && !variantJson.get("chroma").getAsString().isEmpty()) {

                            chromaId = Identifier.parse(
                                    variantJson.get("chroma").getAsString()
                            );
                        }

                        Identifier itemTexture = Identifier.parse(
                                variantJson.get("item_texture").getAsString()
                        );

                        variants.add(new GemVariant(
                                id,
                                name,
                                chromaId,
                                itemTexture
                        ));
                    }
                }

                List<GemAvailability> availability = new ArrayList<>();

                JsonArray availabilityArray = json.has("availability") ? json.getAsJsonArray("availability") : new JsonArray();

                for (JsonElement element : availabilityArray) {
                    JsonObject period = element.getAsJsonObject();

                    MonthDay start = MonthDay.parse(
                            "--" + period.get("start").getAsString()
                    );

                    MonthDay end = MonthDay.parse(
                            "--" + period.get("end").getAsString()
                    );

                    availability.add(
                            new GemAvailability(start, end)
                    );
                }

                int skinVariants = visualVariants.get("skin").getAsInt();
                int hairVariants = visualVariants.get("hair").getAsInt();
                int gemVariants = visualVariants.get("gem").getAsInt();
                int outfitVariants = visualVariants.get("outfit").getAsInt();
                int insigniaVariants = visualVariants.get("insignia").getAsInt();

                JsonObject sounds = json.has("sounds")
                        ? json.getAsJsonObject("sounds")
                        : new JsonObject();

                Identifier instrumentSound = sounds.has("instrument")
                        ? Identifier.parse(sounds.get("instrument").getAsString())
                        : Identifier.withDefaultNamespace("block.note_block.harp");

                GemDimensions defDimensions = new GemDimensions(dimensions.get("width").getAsFloat(), dimensions.get("height").getAsFloat());
                GemStats defStats = new GemStats(stats.get("health").getAsFloat(), stats.get("strength").getAsFloat(), stats.get("speed").getAsFloat());

                GemDefinition definition = new GemDefinition(gemId, defStats, defDimensions, abilities, variants, skinVariants, hairVariants, gemVariants, outfitVariants, insigniaVariants, availability, instrumentSound, parentGem);

                definitions.put(gemId, definition);
                Gemology.LOGGER.info("Loaded gem {} with abilities: {}", gemId, abilities);
                Gemology.LOGGER.info(

                        "Gem {} has parent {} and fusion type {}",

                        definition.getId(),

                        definition.getParentGem(),

                        definition.getFusionTypeId()

                );
                //Gemology.LOGGER.info("Loaded gem definition: {}", gemId);
            } catch (IOException | RuntimeException e) {
                throw new RuntimeException("Failed to load gem definition: " + resourceId, e);
            }
        }

        return definitions;
    }

    @Override
    protected void apply(Map<Identifier, GemDefinition> definitions, ResourceManager resourceManager, ProfilerFiller profiler) {
        manager.clear();

        for (GemDefinition definition : definitions.values()) {
            manager.register(definition);
        }
    }
}