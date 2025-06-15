package item;

import java.io.*;
import java.util.*;

public class ItemService {
    private final String CAMINHO = "../dados/itens.txt";

    public void adicionarItem(Item item) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO, true))) {
            writer.write(item.toString());
            writer.newLine();
            System.out.println("Item cadastrado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar item: " + e.getMessage());
        }
    }

    public List<Item> listarItens() {
        List<Item> lista = new ArrayList<>();
        File arquivo = new File(CAMINHO);

        if (!arquivo.exists()) return lista;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                lista.add(Item.fromString(linha));
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler itens: " + e.getMessage());
        }

        return lista;
    }

    public void atualizarItem(Scanner scanner) {
        List<Item> itens = listarItens();

        System.out.println("=== Atualização de Item ===");
        System.out.print("Digite o código do item que deseja atualizar: ");
        String codigo = scanner.nextLine();

        boolean encontrado = false;

        for (int i = 0; i < itens.size(); i++) {
            Item item = itens.get(i);
            if (String.valueOf(item.getCodigo()).equals(codigo)){
                encontrado = true;
                System.out.println("Item encontrado:");
                System.out.println(item);

                System.out.print("Novo título (ou Enter para manter): ");
                String novoTitulo = scanner.nextLine();
                if (!novoTitulo.isEmpty()) item = new Item(item.getCodigo(), novoTitulo, item.getGenero(), item.getTipo(), item.getPrecoDiario());

                System.out.print("Novo gênero (ou Enter para manter): ");
                String novoGenero = scanner.nextLine();
                if (!novoGenero.isEmpty()) item = new Item(item.getCodigo(), item.getTitulo(), novoGenero, item.getTipo(), item.getPrecoDiario());

                System.out.print("Novo tipo (ou Enter para manter): ");
                String novoTipo = scanner.nextLine();
                if (!novoTipo.isEmpty()) item = new Item(item.getCodigo(), item.getTitulo(), item.getGenero(), novoTipo, item.getPrecoDiario());

                System.out.print("Novo preço diário (ou Enter para manter): ");
                String novoPreco = scanner.nextLine();
                if (!novoPreco.isEmpty()) {
                    try {
                        double preco = Double.parseDouble(novoPreco);
                        item = new Item(item.getCodigo(), item.getTitulo(), item.getGenero(), item.getTipo(), preco);
                    } catch (NumberFormatException e) {
                        System.out.println("Preço inválido, mantendo o anterior.");
                    }
                }

                itens.set(i, item);
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Item com código informado não encontrado.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO))) {
            for (Item item : itens) {
                writer.write(item.toString());
                writer.newLine();
            }
            System.out.println("Item atualizado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao atualizar o item: " + e.getMessage());
        }
    }

    public void removerItemPorCodigo(Scanner scanner) {
        List<Item> itens = listarItens();

        System.out.print("Digite o código do item que deseja remover: ");
        int codigo = scanner.nextInt();
        scanner.nextLine(); // consumir quebra de linha

        boolean removido = itens.removeIf(item -> item.getCodigo() == codigo);

        if (removido) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO))) {
                for (Item item : itens) {
                    writer.write(item.toString());
                    writer.newLine();
                }
                System.out.println("Item removido com sucesso!");
            } catch (IOException e) {
                System.out.println("Erro ao atualizar o arquivo: " + e.getMessage());
            }
        } else {
            System.out.println("Item com o código informado não encontrado.");
        }
    }

    public void listarFilmes() {
        List<Item> itens = listarItens();
        System.out.println("======= Lista de Filmes =====");

        boolean encontrou = false;
        for (Item item : itens) {
            if (item.getTipo().equalsIgnoreCase("filme")) {
                System.out.println(item);
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum filme encontrado.");
        }
    }

    public void alterarStatusItem(Scanner scanner) {
        List<Item> itens = listarItens();

        System.out.println("=== Alterar Status de Item ===");
        System.out.print("Digite o código do item: ");
        int codigo = Integer.parseInt(scanner.nextLine());

        boolean encontrado = false;

        for (Item item : itens) {
            if (item.getCodigo() == codigo) {
                encontrado = true;

                System.out.println("Item encontrado:");
                System.out.println(item);

                System.out.println("Status atual: " + item.getStatus());
                System.out.print("Novo status (disponível, alugado ou manutenção): ");
                String novoStatus = scanner.nextLine().toLowerCase();

                if (!novoStatus.equals("disponível") && !novoStatus.equals("alugado") && !novoStatus.equals("manutenção")) {
                    System.out.println("Status inválido.");
                    return;
                }

                item.setStatus(novoStatus);
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Item com esse código não encontrado.");
            return;
        }

        // Regravar os dados no arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO))) {
            for (Item item : itens) {
                writer.write(item.toString());
                writer.newLine();
            }
            System.out.println("Status atualizado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao atualizar o status: " + e.getMessage());
        }
    }



}
