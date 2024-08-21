package com.jazzkuh.inventorylib.objects;

import com.jazzkuh.inventorylib.listeners.ButtonInventoryClickListener;
import com.jazzkuh.inventorylib.listeners.ButtonInventoryCloseListener;
import com.jazzkuh.inventorylib.objects.icon.Icon;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.inventory.InventoryCloseEvent;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public abstract class Menu extends Inventory {

    protected Inventory inventory;
    protected Component inventoryName;
    protected InventoryType inventoryType;

    protected Player player;

    protected final List<Icon> icons = new ArrayList<>();
    protected final List<Icon> specialIcons = new ArrayList<>();

    public Menu(Component title, InventoryType type) {
        super(type, title);

        this.inventory = this;
        this.inventoryName = title;
        this.inventoryType = type;
    }

    public Icon getIcon(UUID uniqueId) {
        List<Icon> icons = new ArrayList<>(this.icons);
        icons.addAll(this.specialIcons);

        return icons.stream().filter(icon -> icon.getUniqueId().equals(uniqueId)).findFirst().orElse(null);
    }

    public void updateMenu() {
        this.inventory.clear();

        for (Icon icon : this.icons) {
            ItemStack stack = icon.getItem();
            if (icon.getSlot() != null) {
                this.inventory.setItemStack(icon.getSlot(), stack);
                continue;
            }

            this.inventory.addItemStack(stack);
        }
    }

    public void open(Player player) {
        this.player = player;

        this.updateMenu();
        player.openInventory(this.inventory);
    }

    public static void init() {
        MinecraftServer.getGlobalEventHandler().addListener(new ButtonInventoryClickListener());
        MinecraftServer.getGlobalEventHandler().addListener(new ButtonInventoryCloseListener());
    }


    public void onClose(InventoryCloseEvent event) {
        // Override this method to do something when the inventory is closed
    }

    public void onClick(InventoryPreClickEvent event) {
        // Override this method to do something when the inventory is clicked
    }

    public void addItem(Icon icon) {
        this.icons.add(icon);
    }
    public void clearItems() {
        this.icons.clear();
        this.specialIcons.clear();
    }
}
