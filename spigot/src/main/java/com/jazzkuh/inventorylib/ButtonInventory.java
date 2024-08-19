package com.jazzkuh.inventorylib;

import com.jazzkuh.inventorylib.abstraction.BaseInventory;
import com.jazzkuh.inventorylib.button.Button;
import com.jazzkuh.inventorylib.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class ButtonInventory extends BaseInventory {
    public ButtonInventory(Component name, int slots) {
        this.inventoryName = name;
        this.inventorySlots = slots;
    }

    public ButtonInventory(Component name, boolean dynamic) {
        this.inventoryName = name;
        this.dynamic = dynamic;
    }

    @Override
    public void createInventory(Player player) {
        if (this.isDynamic()) {
            int dynamicSize = (int) (Math.ceil((double) (this.buttons.size()) / 9d) * 9);
            if (buttons.isEmpty()) dynamicSize = 9;
            else if (this.buttons.size() >= 54) dynamicSize = 54;

            this.inventorySlots = 9 * 6;
            this.inventory = Bukkit.createInventory(this, dynamicSize, inventoryName);
        } else {
            this.inventory = Bukkit.createInventory(this, inventorySlots, inventoryName);
        }

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