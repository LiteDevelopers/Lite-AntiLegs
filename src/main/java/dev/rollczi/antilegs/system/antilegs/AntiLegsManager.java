/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.antilegs.system.antilegs;

import panda.std.Option;
import panda.std.stream.PandaStream;

import java.util.*;

public class AntiLegsManager {

    private final Set<AntiLegs> antiLegs = new HashSet<>();

    public void registerAntiLeg(AntiLegs antiLegs) {
        this.antiLegs.add(antiLegs);
    }

    public Set<AntiLegs> getAntiLegs() {
        return Collections.unmodifiableSet(antiLegs);
    }

    public Option<AntiLegs> getAntiLegs(String name) {
        return PandaStream.of(antiLegs).find(antiLeg -> antiLeg.getName().equalsIgnoreCase(name));
    }

    public <T extends AntiLegs> Option<AntiLegs> getAntiLegs(Class<T> antiLegsClass) {
        return PandaStream.of(antiLegs).find(antiLeg -> antiLeg.getClass().isAssignableFrom(antiLegsClass));
    }

}
