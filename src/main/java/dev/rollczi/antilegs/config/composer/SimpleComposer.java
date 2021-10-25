/*
 * Copyright (c) 2021 Rollczi
 */

package dev.rollczi.antilegs.config.composer;

import net.dzikoysk.cdn.serialization.Composer;
import net.dzikoysk.cdn.serialization.SimpleDeserializer;
import net.dzikoysk.cdn.serialization.SimpleSerializer;

public interface SimpleComposer<T> extends Composer<T>, SimpleDeserializer<T>, SimpleSerializer<T> {

}
