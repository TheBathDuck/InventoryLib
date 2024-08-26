package com.jazzkuh.inventorylib.objects;

import com.jazzkuh.inventorylib.loader.InventoryLoader;
import com.jazzkuh.inventorylib.objects.icon.Icon;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Getter
public abstract class PaginatedMenu extends Menu {

    @Setter
    private int page = 1;

    private final List<Integer> registeredSlots = new ArrayList<>();

    public PaginatedMenu(Component title, int size) {
        super(title, size);
    }

    public PaginatedMenu(Component title) {
        super(title);
    }

    public PaginatedMenu(Component title, int size, InventoryType type) {
        super(title, size, type);
    }

    @Override
    public void update() {
        this.inventory.clear();
        this.registeredSlots.sort(Integer::compareTo);
        this.setPageItems();

        for (Icon icon : this.specialIcons) {
            if (icon.getSlot() == null) {
                this.inventory.addItem(icon.getItem());
                continue;
            }

            this.inventory.setItem(icon.getSlot(), icon.getItem());
        }

        int item = (this.page - 1) * this.registeredSlots.size();
        for (int registeredSlot : this.registeredSlots) {
            if (item >= this.icons.size()) break;

            Icon icon = this.icons.get(item);
            this.inventory.setItem(registeredSlot, icon.getItem());

            item++;
        }
    }

    public void registerPageSlots(Integer... slots) {
        this.registerPageSlots(Arrays.asList(slots));
    }

    public void registerPageSlots(List<Integer> slots) {
        this.registeredSlots.addAll(slots);
    }

    public void registerPageSlotsBetween(int from, int to) {
        if (from > to) {
            registerPageSlotsBetween(to, from);
            return;
        }

        this.registeredSlots.addAll(IntStream.range(from, to).boxed().toList());
    }

    public void unregisterAllPageSlots() {
        this.registeredSlots.clear();
    }

    private void openPage(int page) {
        this.page = page;
        this.update();
    }

    protected void setPageItems() {
        Icon previousPageItem = this.getPreviousPageItem();
        Icon nextPageItem = this.getNextPageItem();

        previousPageItem.setClickEvent(event -> {
            if (this.page == 1) {
                if (InventoryLoader.getFormattingProvider() == null) return;
                this.player.sendMessage(InventoryLoader.getFormattingProvider().formatError("There is no previous page."));
                return;
            }

            this.openPage(this.page - 1);
        });

        nextPageItem.setClickEvent(event -> {
            if (this.page >= this.getMaxPages()) {
                if (InventoryLoader.getFormattingProvider() == null) return;
                this.player.sendMessage(InventoryLoader.getFormattingProvider().formatError("There is no next page."));
                return;
            }

            this.openPage(this.page + 1);
        });

        if (nextPageItem.getSlot() == null || previousPageItem.getSlot() == null)
            throw new IllegalArgumentException("Next page and previous page items must have a slot.");

        this.addSpecialIcon(nextPageItem);
        this.addSpecialIcon(previousPageItem);
    }

    public void addSpecialIcon(Icon icon) {
        this.specialIcons.add(icon);
    }

    public int getMaxPages() {
        return (int) Math.ceil((double) this.icons.size() / this.registeredSlots.size());
    }

    public abstract Icon getPreviousPageItem();

    public abstract Icon getNextPageItem();
}
