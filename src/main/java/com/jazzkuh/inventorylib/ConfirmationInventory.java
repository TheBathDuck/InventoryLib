package com.jazzkuh.inventorylib;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import com.jazzkuh.inventorylib.button.Button;

public class ConfirmationInventory extends ButtonInventory {
    private final Player player;
    private final Runnable yesTask;
    private final Runnable noTask;

    public ConfirmationInventory(Player player, Component title, ItemStack itemStack, Runnable yesTask, Runnable noTask) {
        super(title, InventoryType.CHEST_2_ROW);
        this.player = player;
        this.yesTask = yesTask;
        this.noTask = noTask;
        
        this.addButton(new Button(4, itemStack, event -> this.player.closeInventory()));

        this.addButton(new Button(11, ItemStack.of(Material.LIME_DYE).withCustomName(Component.text("Yes").color(TextColor.fromHexString("#12ff2a"))), event -> {
            this.yesTask.run();
            this.player.closeInventory();
        }));

        this.addButton(new Button(15, ItemStack.of(Material.BARRIER).withCustomName(Component.text("No").color(TextColor.fromHexString("#FC3838"))), event -> {
            this.noTask.run();
            this.player.closeInventory();
        }));
    }
}
