/*
 * Copyright (c) 2021 Rollczi
 */

package dev.rollczi.antilegs.config.composer;

public class CharacterComposer implements SimpleComposer<Character> {

    @Override
    public Character deserialize(String string) {
        if (string.length() != 1) {
            throw new IllegalArgumentException("Ciąg znaków \"" + string + "\" jest niepoprawny! Oczekiwana długość to 1.");
        }

        return string.charAt(0);
    }

    @Override
    public String serialize(Character character) {
        return character.toString();
    }

}
