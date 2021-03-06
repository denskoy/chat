package server;

import java.util.ArrayList;
import java.util.List;

public class SimpleAuthService implements AuthService {
    private class UserData{
        String login;
        String password;
        String nickname;

        public UserData(String login, String password, String nickname) {
            this.login = login;
            this.password = password;
            this.nickname = nickname;
        }
    }
    List<UserData> users;

    public SimpleAuthService(){
        users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(new UserData("login" + i, "psss" + i, "nick" + i));
        }
        users.add(new UserData("qwe", "qwe", "qwe"));
        users.add(new UserData("asd", "asd", "asd"));

    }
    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
       for (UserData user : users){
           if (user.login.equals(login) && user.password.equals(password)){
               return user.nickname;
           }
       }
       return null;
    }
}
