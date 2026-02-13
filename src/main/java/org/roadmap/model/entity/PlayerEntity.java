package org.roadmap.model.entity;

public class PlayerEntity {
    private Integer id;
    private String name;

    public PlayerEntity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // TEMP: only for testing
    @Override
    public String toString() {
        return "PlayerEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
