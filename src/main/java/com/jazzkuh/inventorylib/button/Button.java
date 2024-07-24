package com.jazzkuh.inventorylib.button;

import lombok.Data;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import com.jazzkuh.inventorylib.item.ItemBuilder;

import java.util.UUID;
import java.util.function.Consumer;

@Data
public final class Button {
    private Integer slot;
    private ItemBuilder item;
    private Consumer<InventoryPreClickEvent> clickEvent;
    private boolean sound;

    private final String identifier;

    public Button(int slot, ItemBuilder item) {
        this.slot = slot;
        this.item = item;
        this.sound = true;
        this.clickEvent = null;
        this.identifier = UUID.randomUUID().toString();
    }

    public Button(int slot, ItemBuilder item, Consumer<InventoryPreClickEvent> clickEvent) {
        this.slot = slot;
        this.item = item;
        this.sound = true;
        this.clickEvent = clickEvent;
        this.identifier = UUID.randomUUID().toString();
    }

    public Button(int slot, ItemBuilder item, boolean hasSound) {
        this.slot = slot;
        this.item = item;
        this.sound = hasSound;
        this.clickEvent = null;
        this.identifier = UUID.randomUUID().toString();
    }

    public Button(int slot, ItemBuilder item, boolean hasSound, Consumer<InventoryPreClickEvent> clickEvent) {
        this.slot = slot;
        this.item = item;
        this.sound = hasSound;
        this.clickEvent = clickEvent;
        this.identifier = UUID.randomUUID().toString();
    }

    public Button(ItemBuilder item) {
        this.slot = null;
        this.item = item;
        this.sound = true;
        this.clickEvent = null;
        this.identifier = UUID.randomUUID().toString();
    }

    public Button(ItemBuilder item, Consumer<InventoryPreClickEvent> clickEvent) {
        this.slot = null;
        this.item = item;
        this.sound = true;
        this.clickEvent = clickEvent;
        this.identifier = UUID.randomUUID().toString();
    }

    public Button(ItemBuilder item, boolean hasSound) {
        this.slot = null;
        this.item = item;
        this.sound = hasSound;
        this.clickEvent = null;
        this.identifier = UUID.randomUUID().toString();
    }

    public Button(ItemBuilder item, boolean hasSound, Consumer<InventoryPreClickEvent> clickEvent) {
        this.item = item;
        this.sound = hasSound;
        this.clickEvent = clickEvent;
        this.identifier = UUID.randomUUID().toString();
    }

    public ItemBuilder getIdentifiedItem() {
        return this.getItem().clone().nbt("button_identifier", this.getIdentifier().toString());
    }
}