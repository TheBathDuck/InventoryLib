package com.jazzkuh.inventorylib;

import com.jazzkuh.inventorylib.item.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.Material;
import com.jazzkuh.inventorylib.item.ItemBuilder;
import com.jazzkuh.inventorylib.button.Button;

public class ConfirmationInventory extends ButtonInventory {
    private final Player player;
    private final Runnable yesTask;
    private final Runnable noTask;

    public ConfirmationInventory(Player player, Component title, ItemBuilder itemBuilder, Runnable yesTask, Runnable noTask) {
        super(title, InventoryType.CHEST_2_ROW);
        this.player = player;
        this.yesTask = yesTask;
        this.noTask = noTask;
        
        this.addButton(new Button(4, itemBuilder, event -> this.player.closeInventory()));

        this.addButton(new Button(11, new ItemBuilder(Material.LIME_DYE).name("<success>Yes"), event -> {
            this.yesTask.run();
            this.player.closeInventory();
        }));

        this.addButton(new Button(15, new ItemBuilder(Material.BARRIER).name("<error>No"), event -> {
            this.noTask.run();
            this.player.closeInventory();
        }));
    }
}
