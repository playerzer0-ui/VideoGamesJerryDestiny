package business;

import java.util.ArrayList;
import java.util.List;

public class UserList {
    private List<User> users;

    public UserList(List<User> users) {
        this.users = users;
    }

    public UserList() {
        users = new ArrayList<>();
    }

    /**
     * login
     * @param username the username
     * @return true or false
     */
    public boolean connectUser(String username){
        User u = new User(username);
        int index = users.indexOf(u);
        return index < 0;
    }
}
