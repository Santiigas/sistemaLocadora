package aluguel;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class AluguelService {
    private final String CAMINHO = "../dados/alugueis.txt";

    public void registrarAluguel(Scanner scanner) {
        System.out.println("=== Registrar Aluguel ===");

        System.out.print("Código do item: ");
        int codigoItem = scanner.nextInt();
        scanner.nextLine(); // limpar quebra de linha

        System.out.print("CPF do cliente: ");
        String cpf = scanner.nextLine();

        System.out.print("Quantidade de dias: ");
        int dias = scanner.nextInt();
        scanner.nextLine();

        Aluguel aluguel = new Aluguel(codigoItem, cpf, LocalDate.now(), dias);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO, true))) {
            writer.write(aluguel.toString());
            writer.newLine();
            System.out.println("Aluguel registrado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao registrar aluguel: " + e.getMessage());
        }
    }

    public List<Aluguel> listarAlugueis() {
        List<Aluguel> lista = new ArrayList<>();
        File arquivo = new File(CAMINHO);

        if (!arquivo.exists()) return lista;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                lista.add(Aluguel.fromString(linha));
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler alugueis: " + e.getMessage());
        }

        return lista;
    }
}
