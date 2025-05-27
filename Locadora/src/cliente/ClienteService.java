package cliente;

import java.io.*;
import java.util.*;

public class ClienteService {
    private final String CAMINHO = "../dados/clientes.txt";

    public void adicionarCliente(Cliente cliente) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO, true))) {
            writer.write(cliente.toString());
            writer.newLine();
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar cliente: " + e.getMessage());
        }
    }

    public List<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        File arquivo = new File(CAMINHO);

        if (!arquivo.exists()) return lista;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                lista.add(Cliente.fromString(linha));
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler clientes: " + e.getMessage());
        }

        return lista;
    }

    public void atualizarCliente(String cpfBuscado, String novoNome) {
        List<Cliente> clientes = listarClientes();
        boolean encontrado = false;

        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpfBuscado)) {
                clientes.set(clientes.indexOf(cliente), new Cliente(novoNome, cpfBuscado));
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(CAMINHO))) {
                for (Cliente cliente : clientes) {
                    writer.write(cliente.toString());
                    writer.newLine();
                }
                System.out.println("Cliente atualizado com sucesso!");
            } catch (IOException e) {
                System.out.println("Erro ao atualizar cliente: " + e.getMessage());
            }
        } else {
            System.out.println("Cliente com CPF " + cpfBuscado + " não encontrado.");
        }
}

}
