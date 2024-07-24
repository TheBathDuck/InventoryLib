package com.jazzkuh.inventorylib.listeners;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.inventory.click.ClickType;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;
import com.jazzkuh.inventorylib.abstraction.BaseInventory;
import com.jazzkuh.inventorylib.button.Button;
import net.minestom.server.tag.Tag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

        if (hasCustomData(itemStack, "button_identifier")) {
            String buttonId = getCustomData(itemStack, "button_identifier");
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

    private boolean hasCustomData(ItemStack itemStack, String key) {
        if (!itemStack.has(ItemComponent.CUSTOM_DATA)) return false;
        return itemStack.get(ItemComponent.CUSTOM_DATA).hasTag(Tag.String(key));
    }

    @Nullable
    private String getCustomData(ItemStack itemStack, String key) {
        if (!itemStack.has(ItemComponent.CUSTOM_DATA)) return null;
        if (!hasCustomData(itemStack, key)) return null;
        return itemStack.get(ItemComponent.CUSTOM_DATA).getTag(Tag.String(key));
    }
}
