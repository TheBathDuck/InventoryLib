package com.jazzkuh.inventorylib.listeners;

import com.jazzkuh.inventorylib.objects.Menu;
import com.jazzkuh.inventorylib.objects.icon.Icon;
import com.jazzkuh.inventorylib.utils.PersistentData;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.UUID;

public final class InventoryListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        ItemStack stack = event.getCurrentItem();

        if (stack == null) return;
        if (event.getInventory().getHolder() == null) return;
        if (!(event.getInventory().getHolder() instanceof Menu menu)) return;

        if (event.getClick() == ClickType.NUMBER_KEY) {
            event.setCancelled(true);
            return;
        }

        event.setCancelled(true);

        if (!PersistentData.contains(stack, "icon_identifier")) {
            menu.onClick(event);
            return;
        }

        UUID iconId = UUID.fromString(Objects.requireNonNull(PersistentData.getString(stack, "icon_identifier")));
        Icon icon = menu.getIcon(iconId);

        if (icon == null || icon.getClickEvent() == null) return;
        if (icon.isSound()) {
            Player player = (Player) event.getWhoClicked();
            player.playSound(Sound.sound(Key.key("ui_button_click"), Sound.Source.PLAYER, 1, 1));
        }

        icon.getClickEvent().accept(event);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() == null) return;
        if (!(event.getInventory().getHolder() instanceof Menu menu)) return;

        menu.onClose(event);
    }

    @EventHandler
    public void onMove(InventoryMoveItemEvent event) {
        if (event.getDestination().getHolder() == null) return;
        if (!(event.getDestination().getHolder() instanceof Menu menu)) return;

        menu.onMove(event);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (event.getInventory().getHolder() == null) return;
        if (!(event.getInventory().getHolder() instanceof Menu menu)) return;

        menu.onDrag(event);
    }

    @EventHandler
    public void onInteract(InventoryInteractEvent event) {
        if (event.getInventory().getHolder() == null) return;
        if (!(event.getInventory().getHolder() instanceof Menu menu)) return;

        menu.onInteract(event);
    }
}
