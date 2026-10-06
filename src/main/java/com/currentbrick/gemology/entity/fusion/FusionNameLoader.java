package com.currentbrick.gemology.entity.fusion;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class FusionNameLoader extends SimplePreparableReloadListener<List<String>> {

    private static final Gson GSON = new Gson();

    private final FusionNameManager manager;

    public FusionNameLoader(FusionNameManager manager) {
        this.manager = manager;
    }

    @Override
    protected List<String> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        List<String> loadedNames = new ArrayList<>();

        for (var entry : resourceManager.listResources("fusion", path -> path.getPath().equals("fusion/names.json")).entrySet()) {

            Identifier resourceId = entry.getKey();
            Resource resource = entry.getValue();

            try (Reader reader = resource.openAsReader()) {
                JsonArray json = GSON.fromJson(reader, JsonArray.class);

                for (JsonElement element : json) {
                    loadedNames.add(element.getAsString());
                }

            } catch (IOException | RuntimeException e) {
                throw new RuntimeException("Failed to load fusion names: " + resourceId, e);
            }
        }

        return loadedNames;
    }

    @Override
    protected void apply(List<String> loadedNames, ResourceManager resourceManager, ProfilerFiller profiler) {
        manager.clear();
        for (String name : loadedNames) {
            manager.register(name);
        }
    }
}