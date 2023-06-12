package dados;

public class Gerente extends Usuario {

    public Gerente(String nome, String login, String senha) {
        super(nome, login, senha);
    }

    public String getTipo() {
        return "Gerente";
    }

}
