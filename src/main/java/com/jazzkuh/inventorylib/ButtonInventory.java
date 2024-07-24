package com.jazzkuh.inventorylib;

import com.jazzkuh.inventorylib.item.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.InventoryType;
import com.jazzkuh.inventorylib.abstraction.BaseInventory;
import com.jazzkuh.inventorylib.button.Button;

public abstract class ButtonInventory extends BaseInventory {
    public ButtonInventory(Component name, InventoryType inventoryType) {
        super(inventoryType, name);
        this.inventoryName = name;
    }

    @Override
    public void createInventory(Player player) {
        for (Button button : this.buttons) {
            ItemBuilder itemBuilder = button.getIdentifiedItem();
            if (button.getSlot() != null) {
                this.inventory.setItemStack(button.getSlot(), itemBuilder.toItemStack());
                continue;
            }

            this.inventory.addItemStack(itemBuilder.toItemStack());
        }

        player.openInventory(inventory);
    }

    @Override
    public final void addButton(Button button) {
        this.buttons.add(button);
    }
}