package user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import persistency.UserPersistencyDB;

public class User {
    private int id;
    private String nickname;
    private String password;
    private Date createdAt;
    private static int counter = 1;

    private static UserPersistencyDB userDB = new UserPersistencyDB();


    public User(String nickname, String password) {
        this.id = counter;
        counter++;
        this.nickname = nickname;
        this.password = password;
        this.createdAt = new Date();
    }


    public static String newUser(String nickname, String password) {
        if (!userExists(nickname)) {
            userDB.save(new User(nickname, password));
            return "Usuário criado com sucesso!";
        }
        return "Usuário já existe!";
    }


    public static List<String> listUsers() {
        List<String> nicknames = new ArrayList<>();
        List<User> list = userDB.list();
        for (User u : list) {
            nicknames.add(u.getId() + "- " + u.getNickname());
        }
        return nicknames;
    }


    public static String editUser(int id, String newNickname) {
        User u = getUserById(id);
        if (u != null && !userExists(newNickname)) {
            u.setNickname(newNickname);
            userDB.update(u);
            return "Usuário alterado com sucesso!";
        }
        return "Usuário não encontrado ou novo nickname já existe!";
    }

    public static String deleteUser(int id) {
        User u = getUserById(id);
        if (u != null) {
            userDB.delete(u.getId());
            return "Usuário excluído com sucesso!";
        }
        return "Usuário não encontrado!";
    }

    // Altera a senha do usuário
    public static String changePassword(int id, String oldPassword, String newPassword) {
        User u = getUserById(id);
        if (u != null && u.getPassword().equals(oldPassword)) {
            u.setPassword(newPassword);
            userDB.update(u);
            return "Senha alterada com sucesso!";
        }
        return "Usuário não encontrado ou senha incorreta!";
    }

    // Realiza o login de um usuário
    public static boolean login(String nickname, String password) {
        List<User> list = userDB.list();
        for (User u : list) {
            if (u.getNickname().equals(nickname) && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    // Verifica se o usuário já existe
    public static boolean userExists(String nickname) {
        List<User> list = userDB.list();
        for (User u : list) {
            if (u.getNickname().equals(nickname)) {
                return true;
            }
        }
        return false;
    }

    // Busca um usuário pelo ID
    public static User getUserById(int id) {
        List<User> list = userDB.list();
        for (User u : list) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
