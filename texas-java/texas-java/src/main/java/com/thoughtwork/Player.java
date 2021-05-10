package com.thoughtwork;

import java.util.Objects;

public class Player {
    private String name;
    private boolean active;
    private boolean tookAction;

    public Player(String name) {
        this.name = name;
        this.active = true;
        this.tookAction = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isTookAction() {
        return tookAction;
    }

    public void setTookAction(boolean tookAction) {
        this.tookAction = tookAction;
    }

}
