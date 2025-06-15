package aluguel;

import java.time.LocalDate;

public class Aluguel {
    private int codigoItem;
    private String cpfCliente;
    private LocalDate dataAluguel;
    private int dias;

    public Aluguel(int codigoItem, String cpfCliente, LocalDate dataAluguel, int dias) {
        this.codigoItem = codigoItem;
        this.cpfCliente = cpfCliente;
        this.dataAluguel = dataAluguel;
        this.dias = dias;
    }

    public int getCodigoItem() {
        return codigoItem;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public LocalDate getDataAluguel() {
        return dataAluguel;
    }

    public int getDias() {
        return dias;
    }

    @Override
    public String toString() {
        return codigoItem + ";" + cpfCliente + ";" + dataAluguel + ";" + dias;
    }

    public static Aluguel fromString(String linha) {
        String[] partes = linha.split(";");
        return new Aluguel(
            Integer.parseInt(partes[0]),
            partes[1],
            LocalDate.parse(partes[2]),
            Integer.parseInt(partes[3])
        );
    }
}
