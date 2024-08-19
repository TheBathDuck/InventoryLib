package com.jazzkuh.inventorylib;

import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.InventoryType;
import com.jazzkuh.inventorylib.abstraction.BaseInventory;
import com.jazzkuh.inventorylib.button.Button;
import net.minestom.server.item.ItemStack;

public abstract class ButtonInventory extends BaseInventory {
    public ButtonInventory(Component name, InventoryType inventoryType) {
        super(inventoryType, name);
        this.inventoryName = name;
    }

    @Override
    public void createInventory(Player player) {
        for (Button button : this.buttons) {
            ItemStack itemStack = button.getIdentifiedItem();
            if (button.getSlot() != null) {
                this.inventory.setItemStack(button.getSlot(), itemStack);
                continue;
            }

            this.inventory.addItemStack(itemStack);
        }

        player.openInventory(inventory);
    }

    @Override
    public final void addButton(Button button) {
        this.buttons.add(button);
    }
}