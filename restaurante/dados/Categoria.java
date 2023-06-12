package dados;

public class Categoria {

    private int codCategoria;
    public String tipoCategoria;

    public Categoria(int codCategoria, String tipoCategoria) {
        this.codCategoria = codCategoria;
        this.tipoCategoria = tipoCategoria;
    }

    public int getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(int codCategoria) {
        this.codCategoria = codCategoria;
    }

    public String getTipoCategoria() {
        return tipoCategoria;
    }

    public void setTipoCategoria(String tipoCategoria) {
        this.tipoCategoria = tipoCategoria;
    }

}
