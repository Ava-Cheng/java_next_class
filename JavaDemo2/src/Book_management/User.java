package Book_management;

public class User {

    public String id;
    public String password;
    public String permission;

    public User(String id, String password, String permission) {
        this.id = id;
        this.password = password;
        this.permission = permission;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getPermission() {
        return permission;
    }
}
