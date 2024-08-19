package com.jazzkuh.inventorylib.abstraction;

import com.jazzkuh.inventorylib.button.Button;
import com.jazzkuh.inventorylib.listeners.ButtonInventoryListeners;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public abstract class BaseInventory implements InventoryHolder {
    protected org.bukkit.inventory.Inventory inventory;
    protected Component inventoryName;
    protected boolean dynamic = false;
    protected int inventorySlots = 0;

    protected final List<Button> buttons = new ArrayList<>();
    protected final List<Button> specialButtons = new ArrayList<>();

    public abstract void createInventory(Player player);
    public abstract void addButton(Button button);

    @Nullable
    public Button getButton(UUID identifier) {
        List<Button> allButtons = new ArrayList<>(this.buttons);
        allButtons.addAll(this.specialButtons);

        return allButtons.stream().filter(button -> button.getIdentifier().equals(identifier)).findFirst().orElse(null);
    }

    public static void init(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(new ButtonInventoryListeners(), plugin);
    }

    public void onClick(InventoryClickEvent event) {
        // Override this method to add a click event to the inventory
    }

    public void onClose(InventoryCloseEvent event) {
        // Override this method to add a close event to the inventory
    }
}