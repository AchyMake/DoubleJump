package org.achymake.doublejump.data;

import org.achymake.doublejump.DoubleJump;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public record Userdata(Player getPlayer) {
    private DoubleJump getInstance() {
        return DoubleJump.getInstance();
    }
    private NamespacedKey getKey(String key) {
        return new NamespacedKey(getInstance(), key);
    }
    private PersistentDataContainer getData() {
        return getPlayer().getPersistentDataContainer();
    }
    public void toggleDoubleJump() {
        var container = getData();
        if (hasDoubleJump()) {
            container.remove(getKey("doubleJump"));
        } else container.set(getKey("doubleJump"), PersistentDataType.BOOLEAN, true);
    }
    public boolean hasDoubleJump() {
        var container = getData();
        return Objects.requireNonNullElse(container.get(getKey("doubleJump"), PersistentDataType.BOOLEAN), false);
    }
    public void resetJump() {
        var container = getData();
        if (container.has(getKey("jumped"), PersistentDataType.INTEGER)) {
            container.remove(getKey("jumped"));
        }
    }
    public void addJump(int value) {
        var container = getData();
        container.set(getKey("jumped"), PersistentDataType.INTEGER, getJumped() + value);
    }
    public int getJumped() {
        var container = getData();
        return Objects.requireNonNullElse(container.get(getKey("jumped"), PersistentDataType.INTEGER), 0);
    }
    @Override
    public Player getPlayer() {
        return getPlayer;
    }
}