package com.jazzkuh.inventorylib.button;

import lombok.Data;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.component.CustomData;

import java.util.UUID;
import java.util.function.Consumer;

@Data
public final class Button {
    private Integer slot;
    private ItemStack item;
    private Consumer<InventoryPreClickEvent> clickEvent;
    private boolean sound;

    private final String identifier;

    public Button(int slot, ItemStack item) {
        this.slot = slot;
        this.item = item;
        this.sound = true;
        this.clickEvent = null;
        this.identifier = UUID.randomUUID().toString();
    }

    public Button(int slot, ItemStack item, Consumer<InventoryPreClickEvent> clickEvent) {
        this.slot = slot;
        this.item = item;
        this.sound = true;
        this.clickEvent = clickEvent;
        this.identifier = UUID.randomUUID().toString();
    }

    public Button(int slot, ItemStack item, boolean hasSound) {
        this.slot = slot;
        this.item = item;
        this.sound = hasSound;
        this.clickEvent = null;
        this.identifier = UUID.randomUUID().toString();
    }

    public Button(int slot, ItemStack item, boolean hasSound, Consumer<InventoryPreClickEvent> clickEvent) {
        this.slot = slot;
        this.item = item;
        this.sound = hasSound;
        this.clickEvent = clickEvent;
        this.identifier = UUID.randomUUID().toString();
    }

    public Button(ItemStack item) {
        this.slot = null;
        this.item = item;
        this.sound = true;
        this.clickEvent = null;
        this.identifier = UUID.randomUUID().toString();
    }

    public Button(ItemStack item, Consumer<InventoryPreClickEvent> clickEvent) {
        this.slot = null;
        this.item = item;
        this.sound = true;
        this.clickEvent = clickEvent;
        this.identifier = UUID.randomUUID().toString();
    }

    public Button(ItemStack item, boolean hasSound) {
        this.slot = null;
        this.item = item;
        this.sound = hasSound;
        this.clickEvent = null;
        this.identifier = UUID.randomUUID().toString();
    }

    public Button(ItemStack item, boolean hasSound, Consumer<InventoryPreClickEvent> clickEvent) {
        this.item = item;
        this.sound = hasSound;
        this.clickEvent = clickEvent;
        this.identifier = UUID.randomUUID().toString();
    }

    public ItemStack getIdentifiedItem() {
        return this.getItem().with(ItemComponent.CUSTOM_DATA, new CustomData(CompoundBinaryTag.builder().putString("button_identifier", this.getIdentifier().toString()).build()));
    }
}