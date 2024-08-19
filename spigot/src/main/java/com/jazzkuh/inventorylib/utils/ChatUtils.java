package com.jazzkuh.inventorylib.utils;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public final class ChatUtils {
    public static Component fromLegacy(String message) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(message);
    }

    public static Component color(String message) {
        MiniMessage extendedInstance = MiniMessage.builder()
                .editTags(tags -> {
                    tags.resolver(TagResolver.resolver("primary", Tag.styling(TextColor.fromHexString("#ff9000"))));
                    tags.resolver(TagResolver.resolver("primary_alt", Tag.styling(TextColor.fromHexString("#FF9812"))));
                    tags.resolver(TagResolver.resolver("success", Tag.styling(TextColor.fromHexString("#12ff2a"))));
                    tags.resolver(TagResolver.resolver("error", Tag.styling(TextColor.fromHexString("#FB465C"))));
                    tags.resolver(TagResolver.resolver("warning", Tag.styling(TextColor.fromHexString("#FBFB00"))));
                })
                .build();

        return extendedInstance.deserialize(message).decoration(TextDecoration.ITALIC, false);
    }

    public static Component prefix(String message) {
        return color(getPrefix()).append(color(message));
    }

    public static Component prefix(String prefix, String message) {
        return color(getPrefix(prefix, "<primary>", "<primary_alt>")).append(color(message));
    }

    public static String getPrefix() {
        return getPrefix("MagicWorld", "<primary>", "<primary_alt>");
    }

    public static String getPrefix(String text, String color, String altColor) {
        return altColor + "•" + color + "● " + text + " <dark_gray>┃ <gray>";
    }

    public static Component formatLinks(String message, String chatColor) {
        ComponentBuilder<TextComponent, TextComponent.Builder> messageComponent = Component.empty().toBuilder();
        Pattern pattern = Pattern.compile("(http|https)://[\\w\\-.]+(:\\d+)?(/\\S*)?");
        Matcher matcher = pattern.matcher(message);

        int lastEnd = 0;
        while (matcher.find()) {
            if (matcher.start() > lastEnd) {
                String beforeUrl = message.substring(lastEnd, matcher.start());
                messageComponent.append(Component.text(beforeUrl).style(ChatUtils.color(chatColor).style()));
            }

            String url = matcher.group();
            messageComponent.append(Component.text(url).style(ChatUtils.color(chatColor).style()).clickEvent(ClickEvent.openUrl(url)));
            lastEnd = matcher.end();
        }

        // Append any remaining text after the last URL
        if (lastEnd < message.length()) {
            String remainingText = message.substring(lastEnd);
            messageComponent.append(Component.text(remainingText).style(ChatUtils.color(chatColor).style()));
        }

        return messageComponent.build();
    }
}