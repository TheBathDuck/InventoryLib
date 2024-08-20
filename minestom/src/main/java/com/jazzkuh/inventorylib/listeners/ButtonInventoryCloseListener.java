package com.jazzkuh.inventorylib.listeners;

import com.jazzkuh.inventorylib.objects.Menu;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

public class ButtonInventoryCloseListener implements EventListener<InventoryCloseEvent> {

    @Override
    public @NotNull Class<InventoryCloseEvent> eventType() {
        return InventoryCloseEvent.class;
    }

    @Override
    public Result run(InventoryCloseEvent event) {
        if (event.getInventory() == null) return Result.SUCCESS;
        if (!(event.getInventory() instanceof Menu menu)) return Result.SUCCESS;

        menu.onClose(event);
        return Result.SUCCESS;
    }
}
