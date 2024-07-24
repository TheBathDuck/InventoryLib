package com.jazzkuh.inventorylib.listeners;

import net.minestom.server.event.EventListener;
import net.minestom.server.event.inventory.InventoryCloseEvent;
import com.jazzkuh.inventorylib.abstraction.BaseInventory;
import org.jetbrains.annotations.NotNull;

public class ButtonInventoryCloseListener implements EventListener<InventoryCloseEvent> {
    @Override
    public @NotNull Class<InventoryCloseEvent> eventType() {
        return InventoryCloseEvent.class;
    }

    @Override
    public Result run(InventoryCloseEvent event) {
        if (event.getInventory() == null) return Result.SUCCESS;
        if (!(event.getInventory() instanceof BaseInventory baseInventory)) return Result.SUCCESS;
        baseInventory.onClose(event);
        return Result.SUCCESS;
    }
}
