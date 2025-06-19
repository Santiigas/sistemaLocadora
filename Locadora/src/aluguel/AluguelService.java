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


    public void listarLocacoesAtivas() {
        List<Aluguel> alugueis = listarAlugueis();
        LocalDate hoje = LocalDate.now();

        System.out.println("=== Locações Ativas ===");
        boolean encontrou = false;

        for (Aluguel aluguel : alugueis) {
            LocalDate devolucaoPrevista = aluguel.getDataAluguel().plusDays(aluguel.getDias());

            if (!hoje.isAfter(devolucaoPrevista)) {
                System.out.println(aluguel);
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhuma locação ativa no momento.");
        }
    }

    public void listarLocacoesAtrasadas() {
        List<Aluguel> alugueis = listarAlugueis();
        LocalDate hoje = LocalDate.now();

        System.out.println("=== Locações em Atraso ===");
        boolean encontrou = false;

        for (Aluguel aluguel : alugueis) {
            LocalDate dataDevolucao = aluguel.getDataAluguel().plusDays(aluguel.getDias());

            if (hoje.isAfter(dataDevolucao)) {
                System.out.println(aluguel);
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhuma locação em atraso.");
        }
    }

    public void calcularMultaPorAtraso(Scanner scanner) {
        List<Aluguel> alugueis = listarAlugueis();
        LocalDate hoje = LocalDate.now();

        System.out.println("=== Calcular Multa por Atraso ===");

        System.out.print("Informe o código do item: ");
        int codigoItem = Integer.parseInt(scanner.nextLine());

        System.out.print("Informe o CPF do cliente: ");
        String cpf = scanner.nextLine();

        for (Aluguel aluguel : alugueis) {
            if (aluguel.getCodigoItem() == codigoItem && aluguel.getCpfCliente().equals(cpf)) {
                LocalDate dataDevolucao = aluguel.getDataAluguel().plusDays(aluguel.getDias());

                if (hoje.isAfter(dataDevolucao)) {
                    long diasAtraso = java.time.temporal.ChronoUnit.DAYS.between(dataDevolucao, hoje);
                    double multa = diasAtraso * 2.0;
                    System.out.printf("Locação está em atraso (%d dias). Multa: R$ %.2f\n", diasAtraso, multa);
                } else {
                    System.out.println("Essa locação ainda está no prazo. Nenhuma multa.");
                }
                return;
            }
        }

        System.out.println("Locação não encontrada.");
    }



}
