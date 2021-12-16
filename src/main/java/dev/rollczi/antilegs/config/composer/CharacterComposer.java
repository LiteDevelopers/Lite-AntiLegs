/*
 * Copyright (c) 2021 Rollczi
 */

package dev.rollczi.antilegs.config.composer;

import panda.std.Result;

public class CharacterComposer implements SimpleComposer<Character> {

    @Override
    public Result<Character, Exception> deserialize(String string) {
        if (string.length() != 1) {
            return Result.error(new IllegalArgumentException("Ciąg znaków \"" + string + "\" jest niepoprawny! Oczekiwana długość to 1."));
        }

        return Result.ok(string.charAt(0));
    }

    @Override
    public Result<String, Exception> serialize(Character character) {
        return Result.ok(character.toString());
    }

}
