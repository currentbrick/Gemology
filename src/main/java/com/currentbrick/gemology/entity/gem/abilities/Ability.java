package com.currentbrick.gemology.entity.gem.abilities;

import com.currentbrick.gemology.entity.EntityGem;

public interface Ability {

    void execute(EntityGem gem, AbilityDefinition definition);
}