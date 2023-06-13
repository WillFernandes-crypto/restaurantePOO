package repositorio;

public interface IUsuario {
    boolean acesso();
    void cadUsuario(String nome, String login, String senha, boolean userGerente, boolean userGarcom);
    boolean verificaCred(String login, String senha);
    void listaUser();
    void delUser(String login);
    void alterarCredenciais(String login);
}
