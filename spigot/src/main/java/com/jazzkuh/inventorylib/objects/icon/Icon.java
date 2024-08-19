package com.jazzkuh.inventorylib.objects.icon;

import com.jazzkuh.inventorylib.utils.PersistentData;
import lombok.Data;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.function.Consumer;

@Data
public final class Icon {

    private UUID uniqueId;
    private ItemStack item;
    private Consumer<InventoryClickEvent> clickEvent;
    private boolean sound;

    private Integer slot;

    public Icon(int slot, ItemStack item) {
        this.slot = slot;
        this.item = item;
        this.sound = true;
        this.clickEvent = null;
        this.uniqueId = UUID.randomUUID();
    }

    public Icon(int slot, ItemStack item, Consumer<InventoryClickEvent> clickEvent) {
        this.slot = slot;
        this.item = item;
        this.sound = true;
        this.clickEvent = clickEvent;
        this.uniqueId = UUID.randomUUID();
    }

    public Icon(int slot, ItemStack item, boolean hasSound) {
        this.slot = slot;
        this.item = item;
        this.sound = hasSound;
        this.clickEvent = null;
        this.uniqueId = UUID.randomUUID();
    }

    public Icon(int slot, ItemStack item, boolean hasSound, Consumer<InventoryClickEvent> clickEvent) {
        this.slot = slot;
        this.item = item;
        this.sound = hasSound;
        this.clickEvent = clickEvent;
        this.uniqueId = UUID.randomUUID();
    }

    public Icon(ItemStack item) {
        this.slot = null;
        this.item = item;
        this.sound = true;
        this.clickEvent = null;
        this.uniqueId = UUID.randomUUID();
    }

    public Icon(ItemStack item, Consumer<InventoryClickEvent> clickEvent) {
        this.slot = null;
        this.item = item;
        this.sound = true;
        this.clickEvent = clickEvent;
        this.uniqueId = UUID.randomUUID();
    }

    public Icon(ItemStack item, boolean hasSound) {
        this.slot = null;
        this.item = item;
        this.sound = hasSound;
        this.clickEvent = null;
        this.uniqueId = UUID.randomUUID();
    }

    public Icon(ItemStack item, boolean hasSound, Consumer<InventoryClickEvent> clickEvent) {
        this.item = item;
        this.sound = hasSound;
        this.clickEvent = clickEvent;
        this.uniqueId = UUID.randomUUID();
    }

    public ItemStack getItem() {
        return this.getItem(true);
    }

    public ItemStack getItem(boolean withIdentifier) {
        if (!withIdentifier) return this.item.clone();

        return PersistentData.set(this.item.clone(), this.uniqueId.toString(), "icon_identifier");
    }

}
