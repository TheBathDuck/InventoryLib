package com.jazzkuh.inventorylib;

import com.jazzkuh.inventorylib.abstraction.BaseInventory;
import com.jazzkuh.inventorylib.button.Button;
import com.jazzkuh.inventorylib.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public abstract class SpecialButtonInventory extends BaseInventory {
    protected final InventoryType inventoryType;

    public SpecialButtonInventory(Component name, InventoryType inventoryType) {
        this.inventoryName = name;
        this.inventoryType = inventoryType;
    }

    @Override
    public void createInventory(Player player) {
        this.inventory = Bukkit.createInventory(this, inventoryType, inventoryName);

        for (Button button : this.buttons) {
            ItemBuilder itemBuilder = button.getIdentifiedItem();
            if (button.getSlot() != null) {
                this.inventory.setItem(button.getSlot(), itemBuilder.toItemStack());
                continue;
            }

            this.inventory.addItem(itemBuilder.toItemStack());
        }

        player.openInventory(inventory);
    }

    @Override
    public final void addButton(Button button) {
        this.buttons.add(button);
    }
}