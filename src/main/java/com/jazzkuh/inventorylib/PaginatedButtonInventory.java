package com.jazzkuh.inventorylib;

import com.jazzkuh.inventorylib.item.ItemBuilder;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.InventoryType;
import com.jazzkuh.inventorylib.item.ItemBuilder;
import com.jazzkuh.inventorylib.abstraction.BaseInventory;
import com.jazzkuh.inventorylib.button.Button;

import java.util.ArrayList;
import java.util.List;

public abstract class PaginatedButtonInventory extends BaseInventory {
    @Setter private int page;
    @Setter private int maxButtons;

    protected PaginatedButtonInventory(Component name, InventoryType inventoryType, int page) {
        super(inventoryType, name);

        this.maxButtons = inventoryType.getSize();
        this.page = page;
    }

    protected PaginatedButtonInventory(Component name, InventoryType inventoryType, int maxButtons, int page) {
        super(inventoryType, name);

        this.maxButtons = maxButtons;
        this.page = page;
    }

    protected abstract ItemBuilder addPreviousPageItem(int previousPage);
    protected abstract ItemBuilder addNextPageItem(int nextPage);

    @Override
    public void createInventory(Player player) {
        ItemBuilder previousPage = this.addPreviousPageItem(this.page - 1);
        ItemBuilder nextPage = this.addNextPageItem(this.page + 1);
        List<Button> pageButtons = this.initPageButtons(previousPage, nextPage);

        for (int i = 0; i < Math.min(this.buttons.size() - this.page * this.inventorySlots, this.inventorySlots); i++) {
            int index = i + page * this.inventorySlots;
            Button button = this.buttons.get(index);
            if (button.getSlot() != null) {
                ItemBuilder itemBuilder = button.getIdentifiedItem();
                this.inventory.setItemStack(button.getSlot(), itemBuilder.toItemStack());
                continue;
            }

            this.inventory.setItemStack(i, this.buttons.get(index).getIdentifiedItem().toItemStack());
        }

        // Special buttons cannot be added to the buttons list because they are not part of the page
        for (Button pageButton : pageButtons) {
            this.inventory.setItemStack(pageButton.getSlot(), pageButton.getIdentifiedItem().toItemStack());
        }

        player.openInventory(inventory);
    }

    @Override
    public final void addButton(Button button) {
        if (button.getSlot() != null) {
            if (button.getSlot() > this.inventorySlots) throw new IllegalArgumentException("Button slot must be less than page size");
        }

        this.buttons.add(button);
    }

    private void openPage(Player player, int page) {
        this.page = page;
        this.createInventory(player);
    }

    private List<Button> initPageButtons(ItemBuilder previousPage, ItemBuilder nextPage) {
        List<Button> pageButtons = new ArrayList<>();
        if (this.page != 0) {
            // Use raw add button method to avoid the page size check
            pageButtons.add(new Button(this.inventorySlots + 3, previousPage, (event) -> this.openPage(event.getPlayer(), this.page - 1)));
        }

        if (this.buttons.size() - (this.page * this.maxButtons) > this.maxButtons) {
            // Use raw add button method to avoid the page size check
            pageButtons.add(new Button(this.inventorySlots + 5, nextPage, (event) -> this.openPage(event.getPlayer(), this.page + 1)));
        }

        this.specialButtons.addAll(pageButtons);
        return pageButtons;
    }
}