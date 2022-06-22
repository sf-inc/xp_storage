package com.github.charlyb01.xpstorage.cardinal;

import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.util.math.random.Random;


public interface RandIntComponent extends Component {
    int getValue();
    void setValue(int value);
    void setRandomValue(int bookAmount, Random random, int lowerBoundRandom, int upperBoundRandom);
}
