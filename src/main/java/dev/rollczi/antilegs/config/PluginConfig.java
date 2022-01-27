/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.antilegs.config;

import com.google.common.collect.ImmutableMap;
import lombok.NoArgsConstructor;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PluginConfig extends AbstractConfigWithResource {

    public PluginConfig(File folder, String child) {
        super(folder, child);
    }

    @Description({
            "#                                           ",
            "# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ #",
            "#                                          #",
            "#           Lite-AntiLegs v2.0.2           #",
            "#                by Rollczi                #",
            "#                                          #",
            "# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ #",
            "#  GitHub: https://github.com/Rollczi      #",
            "#  Website: https://rollczi.dev            #",
            "# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ #",
            "#                                           "
    })

    @Description("")
    @Description("# ~~~~~~~~~~~~ #")
    @Description("#    Komendy   #")
    @Description("# ~~~~~~~~~~~~ #")
    @Description("# AntyNogi - /antilegs ")
    @Description("# (aliasy: /antynogi)")
    @Description("")
    @Description("# ~~~~~~~~~~~~ #")
    @Description("#   Permisje   #")
    @Description("# ~~~~~~~~~~~~ #")
    @Description("# /antilegs give:   dev.rollczi.antilegs.give")
    @Description("# /antilegs reload: dev.rollczi.antilegs.reload")

    @Description("")
    @Description("# ~~~~~~~~~~~~ #")
    @Description("#  Wiadomości  #")
    @Description("# ~~~~~~~~~~~~ #")
    public String useMessage = "&aAnty-Nogi zostały aktywowane!";
    public String useNo = "&cAnty-Nogi nie mogą zostać aktywowane w tej chwili!";
    public String useNoReUse = "&cPoczekaj {SECONDS} sekund ({MILLI_SECONDS}ms) ({SECONDS}.{MILLI_WIHOUT_SECONDS} sek)!";
    public String noFoundPlayer = "&cNie ma takiego gracza!";
    public String noPermission = "&cNie masz permisji do tego polecenia! ({PERMISSIONS})";
    public String giveAntiLegsAdmin = "&aPomyślnie dano antynogi graczowi!";
    public String reload = "&aReloaded configuration!";

    public String giveAntiLegs = "&aDostałeś Antynogi {TYPE}!";

    @Description("")
    @Description("# ~~~~~~~~~~~~ #")
    @Description("#  Ustawienia  #")
    @Description("# ~~~~~~~~~~~~ #")
    @Description("")
    @Description("# Maksymalna odległość działania (bloki)")
    @Description("# domyślnie = 10.0")
    public double distance = 10.0;

    @Description("")
    @Description("# Czas, w którym ważne jest uderzenie (sekundy)")
    @Description("# domyślnie = 15")
    public int secondExpire = 15;

    @Description("")
    @Description("# Cooldown przed następnym użyciem (sekundy)")
    @Description("# domyślnie = 60")
    public int secondCooldown = 60;

    @Description("")
    @Description("# ~~~~~~~~~~~~ #")
    @Description("#     Item     #")
    @Description("# ~~~~~~~~~~~~ #")
    public ItemConfig item = new ItemConfig(
            Material.IRON_BOOTS,
            0,
            "&7AntyNogi",
            Arrays.asList("&7", "&7Kliknij, trzymając w ręce!"),
            Collections.singletonMap(Enchantment.DURABILITY, 10),
            Collections.singletonList(ItemFlag.HIDE_ENCHANTS)
    );

    @Description("")
    @Description("# ~~~~~~~~~~~~~~~~~~~~ #")
    @Description("# #---# Crafting #---#")
    @Description("# ~~~~~~~~~~~~~~~~~~~~ #")
    public Map<Character, Material> crafting = ImmutableMap.<Character, Material>builder()
            .put('1', Material.AIR)
            .put('2', Material.IRON_INGOT)
            .put('3', Material.AIR)
            .put('4', Material.IRON_INGOT)
            .put('5', Material.EMERALD)
            .put('6', Material.IRON_INGOT)
            .put('7', Material.AIR)
            .put('8', Material.IRON_INGOT)
            .put('9', Material.AIR)
            .build();

    @Contextual
    @NoArgsConstructor
    public static class ItemConfig implements Serializable {
        @Description("")
        @Description("# Typ itemu")
        @Description("# 1.16.5 https://helpch.at/docs/1.16.5/org/bukkit/Material.html")
        @Description("# 1.8.8  https://helpch.at/docs/1.8.8/org/bukkit/Material.html")
        public Material material = Material.STONE;

        public int data = 0;

        public String name = "none";

        @Description("# ")
        @Description("# Opis itemku")
        @Description("# jeśli nie chcesz lore ustaw \"lore: []\"")
        public List<String> lore = new ArrayList<>();

        @Description("# ")
        @Description("# Enchant oraz jego level")
        @Description("# W formacie \"ENCHANTMENT: level\"")
        @Description("# przykładowo \"THORNS: 3\"")
        @Description("# jeśli nie chcesz enchantów ustaw \"enchants: []\"")
        @Description("# 1.16.5 https://helpch.at/docs/1.16.5/org/bukkit/enchantments/Enchantment.html")
        @Description("# 1.8.8  https://helpch.at/docs/1.8.8/org/bukkit/enchantments/Enchantment.html")
        public Map<Enchantment, Integer> enchants = new HashMap<>();

        public List<ItemFlag> itemFlags = new ArrayList<>();

        public ItemConfig(Material material, int data, String name, List<String> lore, Map<Enchantment, Integer> enchants, List<ItemFlag> flags) {
            this.material = material;
            this.data = data;
            this.name = name;
            this.lore = lore;
            this.enchants = enchants;
            this.itemFlags = flags;
        }
    }

}