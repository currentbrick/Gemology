package com.currentbrick.gemology.entities.gem.abilities;

import com.currentbrick.gemology.entities.EntityGem;

public interface Ability {

    void execute(EntityGem gem, AbilityDefinition definition);
}