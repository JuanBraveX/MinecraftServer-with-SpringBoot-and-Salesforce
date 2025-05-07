package com.minecraft.server.SpringBootMinecraft;

public class MineMap {
    private boolean weather;
    private String time;
    private String name;
    private int players;
    private String biome;
    private long hour;

    public MineMap(boolean weather, String time, String name, int players, String biome, long hour) {
        this.weather = weather;
        this.time = time;
        this.name = name;
        this.players = players;
        this.biome = biome;
        this.hour = hour;
    }

    public boolean getWeather() {
        return weather;
    }

    public void setWeather(boolean weather) {
        this.weather = weather;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public String getBiome() {
        return biome;
    }

    public void setBiome(String biome) {
        this.biome = biome;
    }

    public long getHour() {
        return hour;
    }

    public void setHour(long hour) {
        this.hour = hour;
    }

    @Override
    public String toString() {
        return "MineMap{" +
                "weather=" + weather +
                ", time='" + time + '\'' +
                ", name='" + name + '\'' +
                ", players=" + players +
                ", biome='" + biome + '\'' +
                ", hour=" + hour +
                '}';
    }
}
