package com.currentbrick.gemology.entities.gem.abilities;

import com.currentbrick.gemology.entities.gem.GemDefinition;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.io.Reader;
import java.util.Map;

public class AbilityDefinitionLoader
        extends SimplePreparableReloadListener<Map<Identifier, AbilityDefinition>> {

    private final AbilityManager manager;

    public AbilityDefinitionLoader(AbilityManager manager) {
        this.manager = manager;
    }

    @Override
    protected Map<Identifier, AbilityDefinition> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        Map<Identifier, AbilityDefinition> loaded = new java.util.HashMap<>();

        resourceManager.listResources("gems/abilities",
                path -> path.getPath().endsWith(".json"))
                .forEach((resourceLocation, resource) -> {

            try (Reader reader = resource.openAsReader()) {

                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();

                Identifier id = Identifier.fromNamespaceAndPath(resourceLocation.getNamespace(),
                        resourceLocation.getPath().substring("gems/abilities/".length())
                                .replace(".json", ""));

                Identifier type = Identifier.parse(json.get("type").getAsString());

                Identifier trigger = Identifier.parse(json.get("trigger").getAsString());

                AbilityDefinition definition = new AbilityDefinition(id, type, trigger, json);

                loaded.put(id, definition);

                System.out.println(
                        "Loaded ability: " + id
                                + " | type: " + type
                                + " | data: " + json
                );

            } catch (Exception e) {
                System.err.println("Failed to load ability: " + resourceLocation);
                e.printStackTrace();
            }
        });

        return loaded;
    }

    @Override
    protected void apply(Map<Identifier, AbilityDefinition> definitions, ResourceManager resourceManager, ProfilerFiller profiler) {
        manager.clear();

        for (AbilityDefinition definition : definitions.values()) {
            manager.register(definition);
        }
    }
}