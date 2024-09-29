package com.jazzkuh.inventorylib.objects;

import com.jazzkuh.inventorylib.listeners.InventoryListener;
import com.jazzkuh.inventorylib.objects.icon.Icon;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public abstract class Menu implements InventoryHolder {

    protected Inventory inventory;
    protected Component inventoryName;
    protected InventoryType inventoryType;
    protected int inventorySize;
    protected boolean dynamic;
    protected boolean cancelClick;

    protected Player player;

    protected final List<Icon> icons = new ArrayList<>();
    protected final List<Icon> specialIcons = new ArrayList<>();

    public Menu(Component title, int size) {
        this(title, size, InventoryType.CHEST);
    }

    public Menu(Component title) {
        this.inventoryName = title;
        this.inventorySize = -1;
        this.inventoryType = InventoryType.CHEST;
        this.dynamic = true;
        this.cancelClick = true;
    }

    public Menu(Component title, int size, InventoryType type) {
        this.inventoryName = title;
        this.inventorySize = size * 9;
        this.inventoryType = type;
        this.dynamic = false;
        this.cancelClick = true;
    }

    public Menu(Component title, int size, InventoryType type, boolean cancelClick) {
        this.inventoryName = title;
        this.inventorySize = size * 9;
        this.inventoryType = type;
        this.dynamic = false;
        this.cancelClick = cancelClick;
    }

    public Menu(Component title, InventoryType type, boolean cancelClick) {
        this.inventoryName = title;
        this.inventoryType = type;
        this.cancelClick = cancelClick;
    }

    public Icon getIcon(UUID uniqueId) {
        List<Icon> icons = new ArrayList<>(this.icons);
        icons.addAll(this.specialIcons);

        return icons.stream().filter(icon -> icon.getUniqueId().equals(uniqueId)).findFirst().orElse(null);
    }

    public void update() {
        this.inventory.clear();

        for (Icon icon : this.icons) {
            ItemStack stack = icon.getItem();
            if (icon.getSlot() != null) {
                this.inventory.setItem(icon.getSlot(), stack);
                continue;
            }

            this.inventory.addItem(stack);
        }
    }

    public void setRows(int amount) {
        if (amount < 1 || amount > 6)
            throw new IllegalArgumentException("Amount must be between 1 and 6");

        this.inventorySize = amount * 9;
    }

    public void open(Player player) {
        if (!Bukkit.isPrimaryThread()) {
            throw new IllegalStateException("Cannot open inventory on async thread.");
        }

        if (this.inventoryType == InventoryType.CHEST) {
            if (this.dynamic) {
                int dynamicSize = (int) (Math.ceil((double) (this.icons.size()) / 9d) * 9);
                if (this.icons.isEmpty()) dynamicSize = 9;
                else if (this.icons.size() >= 54) dynamicSize = 54;

                this.inventorySize = dynamicSize;
            }

            this.inventory = Bukkit.createInventory(this, this.inventorySize, this.inventoryName);
        } else
            this.inventory = Bukkit.createInventory(this, this.inventoryType, this.inventoryName);

        this.player = player;

        this.update();
        player.openInventory(this.inventory);
    }

    public static void init(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), plugin);
    }

    public void onClose(InventoryCloseEvent event) {
        // Override this method to do something when the inventory is closed
    }

    public void onClick(InventoryClickEvent event) {
        // Override this method to do something when the inventory is clicked
    }

    public void onMove(InventoryMoveItemEvent event) {
        // Override this method to do something when an item is moved in the inventory
    }

    public void onDrag(InventoryDragEvent event) {
        // Override this method to do something when an item is dragged in the inventory
    }

    public void onInteract(InventoryInteractEvent event) {
        // Override this method to do something when the inventory is interacted with
    }

    public void addItem(Icon icon) {
        this.icons.add(icon);
    }
    public void clearItems() {
        this.icons.clear();
        this.specialIcons.clear();
    }
}
