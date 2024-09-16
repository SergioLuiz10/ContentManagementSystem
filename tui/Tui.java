package tui;

import java.util.List;
import java.util.Scanner;

import content.Content;
import user.User;

public class Tui {

    static Scanner scan = new Scanner(System.in);
    private static boolean running = true;

    public static void start() {
        boolean isLogged = false;

        while(running) {

            if(!isLogged) {
                isLogged = menu();
            } else {
                userMenu();
            }

        }
        System.out.println("Tchau =)");


    }

    public static boolean menu() {
        int choice;
        System.out.println("1- Lstar conteúdos \n2-Logar \n3- Sair");
        choice = scan.nextInt();
        switch (choice) {
            case 1: {
                Content.listContents();
                break;
            }
            case 2: {
                String nickname;
                String password;

                System.out.println("digite seu nickname");
                nickname = scan.next();

                System.out.println("digite sua senha");
                password = scan.next();


                return User.login(nickname, password);
            }
            default:
                running = false;
        }
        return false;

    }

    public static void userMenu() {
        int choice;

        System.out.println("1- Criar conteúdo."
                + " \n2- Listar conteúdos."
                + " \n3- atualizar um conteúdo."
                + " \n4- Excluir conteúdo."
                + " \n5- Criar Usuário."
                + " \n6- Listar Usuário."
                + " \n7- Alterar Usuário."
                + " \n8- Excluir Usuário."
                + " \n9- Alterar Senha."
                + " \n10- Sair");
        choice = scan.nextInt();

        switch (choice) {
            case 1: {
                System.out.println("digite o título:");
                String newTitle = scan.next();
                System.out.println("digite o conteúdo:");
                String newContent = scan.next();

                Content content = new Content(newTitle, newContent);
                Content.addContent(content);

                break;
            }
            case 2: {
                Content.listContents();

                break;
            }
            case 3: {
                int contentChoice;
                Content updatedContent;


                System.out.println("escolha um conteúdo baseado no seu número");
                Content.listContents();
                contentChoice = scan.nextInt();

                updatedContent = Content.findOne(contentChoice);

                System.out.println("altere o título:");
                updatedContent.setTitle(scan.next());

                System.out.println("altere o contúdo:");
                updatedContent.setContent(scan.next());

                Content.updateContent(updatedContent);

                break;
            }
            case 4: {
                int contentChoice;

                System.out.println("escolha um conteúdo baseado no seu número");
                Content.listContents();
                contentChoice = scan.nextInt();

                Content.deleteContent(contentChoice);

                break;
            }
            case 5: {
                System.out.println("digite o nome:");
                String newName = scan.next();
                System.out.println("digite a senha:");
                String newPassword = scan.next();

                System.out.println(User.newUser(newName, newPassword));

                break;
            }
            case 6: {
                List<String> userList = User.listUsers();

                userList.forEach(userInfo -> {
                    System.out.println("\n"+ userInfo);
                });

                break;
            }
            case 7: {

                System.out.println("digite o número do usuário:");
                int id = scan.nextInt();
                System.out.println("digite o novo nome:");
                String newName = scan.next();

                System.out.println(User.editUser(id, newName));

                break;
            }
            case 8: {
                int userChoice;

                System.out.println("escolha um usuário baseado no seu número para deletá-lo");
                List<String> userList = User.listUsers();

                userList.forEach(userInfo -> {
                    System.out.println("\n"+ userInfo);
                });
                userChoice = scan.nextInt();

                System.out.println(User.deleteUser(userChoice));

                break;
            }
            case 9: {
                int userChoice;

                System.out.println("informe o seu número");
                List<String> userList = User.listUsers();

                userList.forEach(userInfo -> {
                    System.out.println("\n"+ userInfo);
                });
                userChoice = scan.nextInt();

                System.out.println("digite a senha antiga:");
                String oldPassword = scan.next();
                System.out.println("digite a nova senha:");
                String newPassword = scan.next();

                System.out.println(User.changePassword(userChoice, oldPassword, newPassword));

                break;
            }
            default:
                running = false;
        }
    }

}