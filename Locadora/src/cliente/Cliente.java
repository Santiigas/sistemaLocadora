package cliente;

public class Cliente {
    private String nome;
    private String cpf;

    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    @Override
    public String toString() {
        return nome + ";" + cpf;
    }

    public static Cliente fromString(String linha) {
        String[] partes = linha.split(";");
        return new Cliente(partes[0], partes[1]);
    }
}
