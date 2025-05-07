package com.minecraft.server.SpringBootMinecraft;

public class Player {
    private String name, uuid;
    private String x,y,z;

    public Player(String name, String uuid, String x, String y, String z) {
        this.name = name;
        this.uuid = uuid;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", z='" + z + '\'' +
                '}';
    }
}
