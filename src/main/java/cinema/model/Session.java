package cinema.model;

import java.util.Objects;

public class Session {
    private int id;
    private String name;
    private byte[] picture;
    private String description;
    private String date;

    public Session() {
    }

    public Session(int id, String name, byte[] picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
    }

    public Session(String name, byte[] picture, String description) {
        this.name = name;
        this.picture = picture;
        this.description = description;
    }

    public Session(int id, String name, byte[] picture, String description, String date) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.description = description;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Session session = (Session) o;
        return id == session.id && Objects.equals(name, session.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
