/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.antilegs.config;

import net.dzikoysk.cdn.source.Resource;

public interface ConfigWithResource {

    Resource getResource();

    void setResource(Resource resource);

}
