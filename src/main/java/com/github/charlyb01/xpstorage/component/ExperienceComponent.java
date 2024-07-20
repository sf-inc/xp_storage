package com.github.charlyb01.xpstorage.component;

import org.ladysnake.cca.api.v3.component.Component;

public interface ExperienceComponent extends Component {
    int getAmount();

    void setAmount(int amount);
    void setLevel(int level);
}
