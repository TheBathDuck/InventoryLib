package com.jazzkuh.inventorylib.button;

import com.jazzkuh.inventorylib.utils.ItemBuilder;
import lombok.Data;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.UUID;
import java.util.function.Consumer;

@Data
public final class Button {
    private Integer slot;
    private ItemBuilder item;
    private Consumer<InventoryClickEvent> clickEvent;
    private boolean sound;

    private final UUID identifier;

    public Button(int slot, ItemBuilder item) {
        this.slot = slot;
        this.item = item;
        this.sound = true;
        this.clickEvent = null;
        this.identifier = UUID.randomUUID();
    }

    public Button(int slot, ItemBuilder item, Consumer<InventoryClickEvent> clickEvent) {
        this.slot = slot;
        this.item = item;
        this.sound = true;
        this.clickEvent = clickEvent;
        this.identifier = UUID.randomUUID();
    }

    public Button(int slot, ItemBuilder item, boolean hasSound) {
        this.slot = slot;
        this.item = item;
        this.sound = hasSound;
        this.clickEvent = null;
        this.identifier = UUID.randomUUID();
    }

    public Button(int slot, ItemBuilder item, boolean hasSound, Consumer<InventoryClickEvent> clickEvent) {
        this.slot = slot;
        this.item = item;
        this.sound = hasSound;
        this.clickEvent = clickEvent;
        this.identifier = UUID.randomUUID();
    }

    public Button(ItemBuilder item) {
        this.slot = null;
        this.item = item;
        this.sound = true;
        this.clickEvent = null;
        this.identifier = UUID.randomUUID();
    }

    public Button(ItemBuilder item, Consumer<InventoryClickEvent> clickEvent) {
        this.slot = null;
        this.item = item;
        this.sound = true;
        this.clickEvent = clickEvent;
        this.identifier = UUID.randomUUID();
    }

    public Button(ItemBuilder item, boolean hasSound) {
        this.slot = null;
        this.item = item;
        this.sound = hasSound;
        this.clickEvent = null;
        this.identifier = UUID.randomUUID();
    }

    public Button(ItemBuilder item, boolean hasSound, Consumer<InventoryClickEvent> clickEvent) {
        this.item = item;
        this.sound = hasSound;
        this.clickEvent = clickEvent;
        this.identifier = UUID.randomUUID();
    }

    public ItemBuilder getIdentifiedItem() {
        return this.getItem().clone().setNBT("button_identifier", this.getIdentifier().toString());
    }
}