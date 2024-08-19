package com.jazzkuh.inventorylib.listeners;

import com.jazzkuh.inventorylib.abstraction.BaseInventory;
import com.jazzkuh.inventorylib.button.Button;
import com.jazzkuh.inventorylib.utils.PersistentData;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class ButtonInventoryListeners implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        ItemStack itemStack = event.getCurrentItem();
        if (event.getInventory().getHolder() == null) return;
        if (!(event.getInventory().getHolder() instanceof BaseInventory baseInventory)) return;

        if (event.getClick() == ClickType.NUMBER_KEY) {
            event.setCancelled(true);
            return;
        }

        event.setCancelled(true);

        if (itemStack == null) return;
        if (PersistentData.contains(itemStack, "button_identifier")) {
            String buttonId = PersistentData.getString(itemStack, "button_identifier");
            UUID identifier = UUID.fromString(buttonId);

            Button button = baseInventory.getButton(identifier);
            if (button != null) {
                if (button.isSound()) {
                    Player player = (Player) event.getWhoClicked();
                    player.playSound(event.getWhoClicked().getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 1, 1F);
                }

                if (button.getClickEvent() == null) return;
                button.getClickEvent().accept(event);
            }
        } else baseInventory.onClick(event);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() == null) return;
        if (!(event.getInventory().getHolder() instanceof BaseInventory baseInventory)) return;
        baseInventory.onClose(event);
    }
}