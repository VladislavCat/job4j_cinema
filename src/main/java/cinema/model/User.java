package cinema.model;

import java.util.Objects;

public class User {
    private int id;
    private String email;
    private String username;
    private String phone;
    private String password;

    public User() {
    }

    public User(String email, String username, String phone, String password) {
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.password = password;
    }

    public User(int id, String email, String username, String phone, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id && Objects.equals(email, user.email)
                && Objects.equals(username, user.username) && Objects.equals(phone, user.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, username, phone);
    }
}
