package dados;

public class Garcom extends Usuario {

    public Garcom(String nome, String login, String senha) {
        super(nome, login, senha);
    }

    public String getTipo() {
        return "Garçom";
    }

}