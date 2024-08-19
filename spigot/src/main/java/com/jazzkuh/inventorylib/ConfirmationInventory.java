package com.jazzkuh.inventorylib;

import com.jazzkuh.inventorylib.button.Button;
import com.jazzkuh.inventorylib.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ConfirmationInventory extends ButtonInventory {
    private final Player player;
    private final Runnable yesTask;
    private final Runnable noTask;

    public ConfirmationInventory(Player player, Component title, ItemBuilder itemBuilder, Runnable yesTask, Runnable noTask) {
        super(title, 18);
        this.player = player;
        this.yesTask = yesTask;
        this.noTask = noTask;

        this.addButton(new Button(4, itemBuilder, event -> this.player.closeInventory()));

        this.addButton(new Button(11, new ItemBuilder(Material.LIME_DYE).setName("<success>Yes"), event -> {
            this.yesTask.run();
            this.player.closeInventory();
        }));

        this.addButton(new Button(15, new ItemBuilder(Material.BARRIER).setName("<error>No"), event -> {
            this.noTask.run();
            this.player.closeInventory();
        }));
    }
}