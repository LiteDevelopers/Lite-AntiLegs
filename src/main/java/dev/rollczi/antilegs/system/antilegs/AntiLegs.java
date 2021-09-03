/*
 * Copyright (c) 2021 Rollczi
 */

package dev.rollczi.antilegs.system.antilegs;

import dev.rollczi.antilegs.utils.ItemBuilder;
import org.bukkit.entity.Player;

public interface AntiLegs {

    /**
     * @return name of AntiLegs
     */

    String getName();

    /**
     * @return a copy of the ItemBuilder of the AntiLegs representation
     */

    ItemBuilder getItem();

    /**
     * Set the ItemBuilder of the AntiLegs representation
     *
     * @param item the ItemBuilder of the AntiLegs representation
     */

    void setItem(ItemBuilder item);

    /**
     * @return the maximum distance from which AntiLegs can be used
     */

    double getDistance();

    /**
     * Set the distance.
     *
     * @param distance the maximum distance from which AntiLegs can be used
     */

    void setDistance(double distance);

    /**
     * @return cooldown before the next use in seconds
     */

    int getCooldown();

    /**
     * Set the cooldown.
     *
     * @param cooldown cooldown before the next use in seconds
     */

    void setCooldown(int cooldown);

    /**
     * Try to use AntiLegs on the player.
     *
     * @param player the player on which AntiLegs are to be used
     * @return true if AntiLegs were used on the player
     */

    boolean use(Player player);

}
