package item;

public class Item {
    private int codigo;
    private String titulo;
    private String genero;
    private String tipo;
    private double precoDiario;

    public Item(int codigo, String titulo, String genero, String tipo, double precoDiario) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.genero = genero;
        this.tipo = tipo;
        this.precoDiario = precoDiario;
    }


    public int getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getGenero() {
        return genero;
    }

    public String getTipo() {
        return tipo;
    }

    public double getPrecoDiario() {
        return precoDiario;
    }

    @Override
    public String toString() {
        return codigo + ";" + titulo + ";" + genero + ";" + tipo + ";" + ";" + precoDiario;
    }

    public static Item fromString(String linha) {
        String[] partes = linha.split(";");
        return new Item(
            Integer.parseInt(partes[0]), 
            partes[1],                   
            partes[2],                   
            partes[3],                     
            Double.parseDouble(partes[5]) 
        );
    }

}
