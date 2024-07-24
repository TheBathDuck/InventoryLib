package com.jazzkuh.inventorylib.listeners;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.inventory.click.ClickType;
import net.minestom.server.item.ItemStack;
import com.jazzkuh.inventorylib.item.ItemBuilder;
import com.jazzkuh.inventorylib.abstraction.BaseInventory;
import com.jazzkuh.inventorylib.button.Button;
import org.jetbrains.annotations.NotNull;

public class ButtonInventoryClickListener implements EventListener<InventoryPreClickEvent> {
    @Override
    public @NotNull Class<InventoryPreClickEvent> eventType() {
        return InventoryPreClickEvent.class;
    }

    @Override
    public Result run(InventoryPreClickEvent event) {
        ItemStack itemStack = event.getClickedItem();
        if (event.getInventory() == null) return Result.SUCCESS;
        if (!(event.getInventory() instanceof BaseInventory baseInventory)) return Result.SUCCESS;

        if (event.getClickType() != ClickType.LEFT_CLICK && event.getClickType() != ClickType.RIGHT_CLICK) {
            event.setCancelled(true);
            return Result.SUCCESS;
        }

        event.setCancelled(true);

        if (ItemBuilder.hasData(itemStack, "button_identifier")) {
            String buttonId = ItemBuilder.getData(itemStack, "button_identifier");
            Button button = baseInventory.getButton(buttonId);
            if (button != null) {
                if (button.isSound()) {
                    Player player = event.getPlayer();
                    player.playSound(Sound.sound(Key.key("ui_button_click"), Sound.Source.PLAYER, 1, 1));
                }

                if (button.getClickEvent() == null) return Result.SUCCESS;
                button.getClickEvent().accept(event);
            }
        } else baseInventory.onClick(event);
        return Result.SUCCESS;
    }
}
