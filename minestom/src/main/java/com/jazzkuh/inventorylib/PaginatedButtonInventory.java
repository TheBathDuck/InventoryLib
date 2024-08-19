package com.jazzkuh.inventorylib;

import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.InventoryType;
import com.jazzkuh.inventorylib.abstraction.BaseInventory;
import com.jazzkuh.inventorylib.button.Button;
import net.minestom.server.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Setter
public abstract class PaginatedButtonInventory extends BaseInventory {
    private int page;
    private int maxButtons;

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

    protected abstract ItemStack addPreviousPageItem(int previousPage);
    protected abstract ItemStack addNextPageItem(int nextPage);

    @Override
    public void createInventory(Player player) {
        ItemStack previousPage = this.addPreviousPageItem(this.page - 1);
        ItemStack nextPage = this.addNextPageItem(this.page + 1);
        List<Button> pageButtons = this.initPageButtons(previousPage, nextPage);

        for (int i = 0; i < Math.min(this.buttons.size() - this.page * this.inventorySlots, this.inventorySlots); i++) {
            int index = i + page * this.inventorySlots;
            Button button = this.buttons.get(index);
            if (button.getSlot() != null) {
                ItemStack itemStack = button.getIdentifiedItem();
                this.inventory.setItemStack(button.getSlot(), itemStack);
                continue;
            }

            this.inventory.setItemStack(i, this.buttons.get(index).getIdentifiedItem());
        }

        // Special buttons cannot be added to the buttons list because they are not part of the page
        for (Button pageButton : pageButtons) {
            this.inventory.setItemStack(pageButton.getSlot(), pageButton.getIdentifiedItem());
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

    private List<Button> initPageButtons(ItemStack previousPage, ItemStack nextPage) {
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