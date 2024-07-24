package com.jazzkuh.inventorylib.item;

import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.minestom.server.color.Color;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.component.*;
import net.minestom.server.item.enchant.Enchantment;
import net.minestom.server.registry.DynamicRegistry;
import net.minestom.server.tag.Tag;
import net.minestom.server.utils.Unit;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemBuilder {
    private ItemStack itemStack;

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder(Material material, int amount) {
        itemStack = ItemStack.of(material, amount);
    }

    public ItemBuilder clone() {
        return new ItemBuilder(itemStack.builder().build());
    }

    public ItemBuilder nbt(String key, Object value) {
        itemStack = itemStack.with(ItemComponent.CUSTOM_DATA, new CustomData(CompoundBinaryTag.builder().putString(key, value.toString()).build()));
        return this;
    }

    public ItemBuilder type(Material material) {
        itemStack = itemStack.withMaterial(material);
        return this;
    }

    public ItemBuilder unbreakable() {
        itemStack = itemStack.with(ItemComponent.UNBREAKABLE, new Unbreakable());
        return this;
    }

    public ItemBuilder setDurability(int durability) {
        itemStack = itemStack.with(ItemComponent.DAMAGE, durability);
        return this;
    }

    public ItemBuilder name(Component name) {
        itemStack = itemStack.withCustomName(name);
        return this;
    }

    public ItemBuilder name(String name) {
        itemStack = itemStack.withCustomName(format(name));
        return this;
    }

    public ItemBuilder addEnchantments(Map<DynamicRegistry.Key<Enchantment>, Integer> enchantments) {
        itemStack = itemStack.with(ItemComponent.ENCHANTMENTS, new EnchantmentList(enchantments));
        return this;
    }

    public ItemBuilder lore(Component... lore) {
        itemStack = itemStack.with(ItemComponent.LORE, Arrays.asList(lore));
        return this;
    }

    public ItemBuilder lore(List<Component> lore) {
        itemStack = itemStack.with(ItemComponent.LORE, lore);
        return this;
    }

    public ItemBuilder removeLore(Component line) {
        itemStack = itemStack.with(ItemComponent.LORE, itemStack.get(ItemComponent.LORE).stream().filter(l -> !l.equals(line)).toList());
        return this;
    }

    public ItemBuilder removeLore(int index) {
        itemStack = itemStack.with(ItemComponent.LORE, itemStack.get(ItemComponent.LORE).stream().filter(l -> l != itemStack.get(ItemComponent.LORE).get(index)).toList());
        return this;
    }

    public ItemBuilder lore(String line) {
        itemStack = itemStack.with(ItemComponent.LORE, itemStack.get(ItemComponent.LORE).stream().map(l -> l.append(format(line))).toList());
        return this;
    }

    public ItemBuilder lore(Component line) {
        itemStack = itemStack.with(ItemComponent.LORE, itemStack.get(ItemComponent.LORE).stream().map(l -> l.append(line)).toList());
        return this;
    }

    public ItemBuilder dyeColor(Color color) {
        itemStack = itemStack.with(ItemComponent.DYED_COLOR, new DyedItemColor(color));
        return this;
    }

    public ItemBuilder profile(PlayerSkin playerSkin) {
        itemStack = itemStack.with(ItemComponent.PROFILE, new HeadProfile(playerSkin));
        return this;
    }

    public ItemBuilder hideComponents() {
        itemStack = itemStack.with(ItemComponent.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE);
        return this;
    }

    public ItemBuilder hideTooltip() {
        itemStack = itemStack.with(ItemComponent.HIDE_TOOLTIP, Unit.INSTANCE);
        return this;
    }

    public ItemBuilder customModelData(Integer data) {
        itemStack = itemStack.with(ItemComponent.CUSTOM_MODEL_DATA, data);
        return this;
    }

    public ItemBuilder attributeModifier(List<AttributeList.Modifier> modifiers) {
        itemStack = itemStack.with(ItemComponent.ATTRIBUTE_MODIFIERS, new AttributeList(modifiers));
        return this;
    }

    public ItemStack toItemStack() {
        return itemStack;
    }

    public static boolean hasData(ItemStack itemStack, String key) {
        if (!itemStack.has(ItemComponent.CUSTOM_DATA)) return false;
        return itemStack.get(ItemComponent.CUSTOM_DATA).hasTag(Tag.String(key));
    }

    @Nullable
    public static String getData(ItemStack itemStack, String key) {
        if (!itemStack.has(ItemComponent.CUSTOM_DATA)) return null;
        if (!hasData(itemStack, key)) return null;
        return itemStack.get(ItemComponent.CUSTOM_DATA).getTag(Tag.String(key));
    }


    private Component format(String message) {
        MiniMessage extendedInstance = MiniMessage.builder()
                .editTags(tags -> {
                    tags.resolver(TagResolver.resolver("success", net.kyori.adventure.text.minimessage.tag.Tag.styling(TextColor.fromHexString("#12ff2a"))));
                    tags.resolver(TagResolver.resolver("error", net.kyori.adventure.text.minimessage.tag.Tag.styling(TextColor.fromHexString("#FC3838"))));
                    tags.resolver(TagResolver.resolver("warning", net.kyori.adventure.text.minimessage.tag.Tag.styling(TextColor.fromHexString("#FBFB00"))));
                }).build();

        return extendedInstance.deserialize(message).decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE);
    }
}