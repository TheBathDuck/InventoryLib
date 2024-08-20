package com.jazzkuh.inventorylib.formatter;

import net.kyori.adventure.text.Component;

public interface InventoryFormattingProvider {
    Component formatError(String message);
}