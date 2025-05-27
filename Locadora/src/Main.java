import cliente.Cliente;
import cliente.ClienteService;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ClienteService clienteService = new ClienteService();

        while (true) {
            System.out.println("\n===== MENU LOCADORA =====");
            System.out.println("1 - Cadastrar novo cliente");
            System.out.println("2 - Listar clientes");
            System.out.println("3 - Atualizar cliente");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    System.out.print("Nome do cliente: ");
                    String nome = scanner.nextLine();
                    System.out.print("CPF do cliente: ");
                    String cpf = scanner.nextLine();
                    Cliente cliente = new Cliente(nome, cpf);
                    clienteService.adicionarCliente(cliente);
                    break;
                case "2":
                    List<Cliente> lista = clienteService.listarClientes();
                    if (lista.isEmpty()) {
                        System.out.println("Nenhum cliente encontrado.");
                    } else {
                        System.out.println("Clientes cadastrados:");
                        for (Cliente c : lista) {
                            System.out.println("- " + c.getNome() + " | CPF: " + c.getCpf());
                        }
                    }
                    break;
                case "3":
                    System.out.print("Informe o CPF do cliente a ser atualizado: ");
                    String cpfAtualizar = scanner.nextLine();
                    System.out.print("Novo nome do cliente: ");
                    String novoNome = scanner.nextLine();
                    clienteService.atualizarCliente(cpfAtualizar, novoNome);
                    break;
                case "0":
                    System.out.println("Encerrando...");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
