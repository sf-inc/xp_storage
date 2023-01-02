package com.github.charlyb01.xpstorage.cardinal;

import dev.onyxstudios.cca.api.v3.component.Component;


public interface ExperienceComponent extends Component {
    int getAmount();
    int getLevel();

    void setAmount(int amount);
    void setLevel(int level);
}
