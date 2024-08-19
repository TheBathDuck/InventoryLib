package com.jazzkuh.inventorylib.abstraction;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.inventory.InventoryCloseEvent;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import com.jazzkuh.inventorylib.button.Button;
import com.jazzkuh.inventorylib.listeners.ButtonInventoryClickListener;
import com.jazzkuh.inventorylib.listeners.ButtonInventoryCloseListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class BaseInventory extends Inventory {
    protected Component inventoryName;
    protected Inventory inventory;
    protected int inventorySlots = 0;

    protected final List<Button> buttons = new ArrayList<>();
    protected final List<Button> specialButtons = new ArrayList<>();

    public BaseInventory(@NotNull InventoryType inventoryType, @NotNull Component title) {
        super(inventoryType, title);
        this.inventory = this;
    }

    public abstract void createInventory(Player player);
    public abstract void addButton(Button button);

    @Nullable
    public Button getButton(String identifier) {
        List<Button> allButtons = new ArrayList<>(this.buttons);
        allButtons.addAll(this.specialButtons);

        return allButtons.stream().filter(button -> button.getIdentifier().equals(identifier)).findFirst().orElse(null);
    }

    public static void init() {
        MinecraftServer.getGlobalEventHandler().addListener(new ButtonInventoryClickListener());
        MinecraftServer.getGlobalEventHandler().addListener(new ButtonInventoryCloseListener());
    }

    public void onClick(InventoryPreClickEvent event) {
        // Override this method to add a click event to the inventory
    }

    public void onClose(InventoryCloseEvent event) {
        // Override this method to add a close event to the inventory
    }
}