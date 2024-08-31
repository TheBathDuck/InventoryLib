package com.jazzkuh.inventorylib.listeners;

import com.jazzkuh.inventorylib.objects.Menu;
import com.jazzkuh.inventorylib.objects.icon.Icon;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.inventory.click.ClickType;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.tag.Tag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ButtonInventoryClickListener implements EventListener<InventoryPreClickEvent> {
    
    @Override
    public @NotNull Class<InventoryPreClickEvent> eventType() {
        return InventoryPreClickEvent.class;
    }

    @Override
    public Result run(InventoryPreClickEvent event) {
        ItemStack itemStack = event.getClickedItem();
        if (event.getPlayer().getOpenInventory() == null) return Result.SUCCESS;
        if (!(event.getPlayer().getOpenInventory() instanceof Menu menu)) return Result.SUCCESS;

        if (event.getClickType() != ClickType.LEFT_CLICK && event.getClickType() != ClickType.RIGHT_CLICK) {
            if (menu.isCancelClick()) event.setCancelled(true);
            return Result.SUCCESS;
        }

        if (menu.isCancelClick()) event.setCancelled(true);

        if (hasCustomData(itemStack, "icon_identifier")) {
            String buttonId = getCustomData(itemStack, "icon_identifier");
            assert buttonId != null;
            Icon icon = menu.getIcon(UUID.fromString(buttonId));
            if (icon != null) {
                if (icon.isSound()) {
                    Player player = event.getPlayer();
                    player.playSound(Sound.sound(Key.key("minecraft:ui_button_click"), Sound.Source.PLAYER, 1, 1));
                }

                if (icon.getClickEvent() == null) return Result.SUCCESS;
                icon.getClickEvent().accept(event);
            }
        } else menu.onClick(event);
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
