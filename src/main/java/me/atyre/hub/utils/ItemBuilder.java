package me.atyre.hub.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {
    private ItemStack item;

    public ItemBuilder(Material material, int amount) {
        item = new ItemStack(material, amount);
    }

    public ItemBuilder(Material material) {
        item = new ItemStack(material);
    }

    public ItemBuilder(ItemStack itemStack) {
        item = itemStack;
    }

    public ItemBuilder name(String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder amount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder maxSizeAmount() {
        item.setAmount(item.getMaxStackSize());
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder lore(String[] lore) {
        List<String> loreList = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();

        for (String s : lore) {
            loreList.add(s);
        }

        meta.setLore(loreList);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder durability(short durability) {
        item.setDurability(durability);
        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment, int level) {
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(enchantment, level, true);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        ItemMeta meta = item.getItemMeta();
        meta.removeEnchant(enchantment);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder enchantments(Enchantment[] enchantments) {
        return this;
    }

    public ItemBuilder enchantments(List<Enchantment> enchantments) {
        return this;
    }

    public ItemBuilder skullOwner(UUID player) {
        return this;
    }

    public ItemStack build() {
        return item;
    }
}
