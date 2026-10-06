package com.currentbrick.gemology.entity.fusion;

import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;

public class FusionNameManager {

    private final List<String> names = new ArrayList<>();

    public void register(String name) {
        names.add(name);
    }

    public void clear() {
        names.clear();
    }

    public List<String> getNames() {
        return names;
    }

    public String getName(long fusionId) {
        if (names.isEmpty()) {
            return "Fusion";
        }

        int index = Math.floorMod(Long.hashCode(fusionId), names.size());

        return names.get(index);
    }
}