package codigo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class XuBank {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cliente cliente = null;
        boolean sair = false;

        while (!sair) {
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Criar cliente");
            System.out.println("2 - Acessar conta de cliente existente");
            System.out.println("0 - Sair");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    // Criação de um novo cliente
                    System.out.println("Digite o nome do cliente:");
                    String nome = scanner.nextLine();

                    System.out.println("Digite o CPF do cliente:");
                    String cpf = scanner.nextLine();

                    System.out.println("Digite a senha do cliente:");
                    String senha = scanner.nextLine();

                    System.out.println("Escolha o tipo de cliente:");
                    System.out.println("1. Regular");
                    System.out.println("2. Gold");
                    System.out.println("3. VIP");

                    int tipoCliente = scanner.nextInt();
                    scanner.nextLine(); // Consumir a quebra de linha

                    switch (tipoCliente) {
                        case 1:
                            cliente = new ClienteRegular(nome, cpf, senha, TipoCliente.REGULAR);
                            break;
                        case 2:
                            cliente = new ClienteGold(nome, cpf, senha, TipoCliente.GOLD);
                            break;
                        case 3:
                            cliente = new ClienteVIP(nome, cpf, senha, TipoCliente.VIP);
                            break;
                        default:
                            System.out.println("Tipo de cliente inválido!");
                            break;
                    }

                    // Adicionar o cliente ao arquivo
                    Cliente.adicionarClienteAoArquivo(nome, cpf, senha, cliente.getTipo());
                    break;
                case 2:
                    // Verificar se o CPF e senha correspondem a um cliente existente
                    System.out.println("Digite o CPF do cliente:");
                    String cpfLogin = scanner.nextLine();
                    System.out.println("Digite a senha do cliente:");
                    String senhaLogin = scanner.nextLine();

                    cliente = lerClienteDoArquivo(cpfLogin, senhaLogin);

                    if (cliente != null) {
                        System.out.println("Bem-vindo, " + cliente.getNome() + " (" + Cliente.getCpf() + ")");
                        boolean clienteLogado = true;

                        while (clienteLogado) {
                            System.out.println("Escolha uma opção:");
                            System.out.println("1 - Criar conta");
                            System.out.println("2 - Depositar");
                            System.out.println("3 - Sacar");
                            System.out.println("4 - Transferir");
                            System.out.println("5 - Consultar saldo");
                            System.out.println("6 - Atualizar saldo");
                            System.out.println("7 - Trocar pontos de fidelidade por recompensas");
                            System.out.println("0 - Sair da conta");

                            int opcaoConta = scanner.nextInt();
                            scanner.nextLine(); // Consumir a quebra de linha

                            switch (opcaoConta) {
                                case 1:
                                    // Criação de uma conta
                                    System.out.println("Digite o tipo de conta a ser criada:");
                                    System.out.println("1 - Conta Corrente");
                                    System.out.println("2 - Poupança");
                                    System.out.println("3 - Renda Fixa");
                                    System.out.println("4 - Investimento");

                                    int tipoConta = scanner.nextInt();
                                    scanner.nextLine(); // Consumir a quebra de linha

                                    switch (tipoConta) {
                                        case 1:
                                            cliente.adicionarConta(new ContaCorrente(cliente));
                                            System.out.println("Conta corrente criada com sucesso!");
                                            break;
                                        case 2:
                                            cliente.adicionarConta(new Poupanca(cliente));
                                            System.out.println("Conta poupança criada com sucesso!");
                                            break;
                                        case 3:
                                            System.out.println(
                                                    "Digite o rendimento contratado para a conta de renda fixa:");
                                            double rendimentoContratado = scanner.nextDouble();
                                            scanner.nextLine(); // Consumir a quebra de linha
                                            cliente.adicionarConta(new RendaFixa(cliente, rendimentoContratado));
                                            System.out.println("Conta de renda fixa criada com sucesso!");
                                            break;
                                        case 4:
                                            cliente.adicionarConta(new Investimento(cliente));
                                            System.out.println("Conta de investimento criada com sucesso!");
                                            break;
                                        default:
                                            System.out.println("Tipo de conta inválido!");
                                    }
                                    break;
                                case 2:
                                    // Depósito
                                    System.out.println("Digite o valor a ser depositado:");
                                    double valorDeposito = scanner.nextDouble();
                                    scanner.nextLine(); // Consumir a quebra de linha
                                    System.out.println("Digite o CPF do cliente para depósito:");
                                    String cpfDeposito = scanner.nextLine();

                                    if (cliente != null && cpfDeposito.equals(Cliente.getCpf())) {
                                        cliente.depositar(valorDeposito);
                                        System.out.println("Depósito realizado com sucesso!");
                                    } else {
                                        System.out.println(
                                                "Cliente não encontrado ou CPF incorreto. Depósito não realizado.");
                                    }
                                    break;

                                case 3:
                                    // Saque
                                    System.out.println("Digite o valor a ser sacado:");
                                    double valorSaque = scanner.nextDouble();
                                    scanner.nextLine(); // Consumir a quebra de linha

                                    System.out.println("Digite o CPF do cliente para saque:");
                                    String cpfSaque = scanner.nextLine();

                                    if (cliente != null && cpfSaque.equals(Cliente.getCpf())) {
                                        cliente.sacar(valorSaque, cpfSaque);
                                        System.out.println("Saque realizado com sucesso!");
                                    } else {
                                        System.out.println(
                                                "Cliente não encontrado ou CPF incorreto. Saque não realizado.");
                                    }
                                    break;

                                case 4:
                                    // Transferência
                                    System.out.println("Digite o valor a ser transferido:");
                                    double valorTransferencia = scanner.nextDouble();
                                    scanner.nextLine(); // Consumir a quebra de linha
                                    System.out.println("Digite o índice da conta de origem para transferência:");
                                    int indiceContaOrigem = scanner.nextInt();
                                    scanner.nextLine(); // Consumir a quebra de linha
                                    System.out.println("Digite o índice da conta de destino para transferência:");
                                    int indiceContaDestino = scanner.nextInt();
                                    scanner.nextLine(); // Consumir a quebra de linha
                                    cliente.transferir(valorTransferencia, indiceContaOrigem, indiceContaDestino);
                                    break;
                                case 5:
                                    // Consulta de saldo
                                    cliente.consultarSaldo();
                                    break;
                                case 6:
                                    // Atualização de saldo
                                    for (Conta conta : cliente.getContas()) {
                                        conta.atualizarSaldo();
                                    }
                                    break;
                                case 7:
                                    // Trocar pontos de fidelidade por recompensas
                                    System.out.println("Digite a quantidade de pontos que deseja usar:");
                                    int pontos = scanner.nextInt();
                                    scanner.nextLine(); // Consumir a quebra de linha
                                    if (cliente instanceof ClienteGold) {
                                        int pontosFidelidade = ((ClienteGold) cliente).getPontosFidelidade();
                                        if (pontosFidelidade >= pontos) {
                                            // Implemente a lógica de troca de pontos por recompensas aqui
                                            // Atualize os pontos de fidelidade do cliente
                                            ((ClienteGold) cliente).setPontosFidelidade(pontosFidelidade - pontos);
                                            System.out.println("Recompensa obtida com sucesso!");
                                        } else {
                                            System.out.println("Pontos de fidelidade insuficientes!");
                                        }
                                    } else {
                                        System.out.println("Essa funcionalidade é exclusiva para clientes Gold.");
                                    }
                                    break;
                                case 0:
                                    clienteLogado = false; // Sair da conta do cliente
                                    break;
                                default:
                                    System.out.println("Opção inválida!");
                            }
                        }
                    } else {
                        System.out.println("CPF ou senha incorreta!");
                    }
                    break;
                case 0:
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }

        scanner.close();
    }

    public static Cliente lerClienteDoArquivo(String cpfLogin, String senhaLogin) {
        try (BufferedReader reader = new BufferedReader(new FileReader("clientes.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 4) {
                    String cpf = partes[1];
                    String senha = partes[2];
                    if (cpf.equals(cpfLogin) && senha.equals(senhaLogin)) {
                        TipoCliente tipoCliente = TipoCliente.valueOf(partes[3]);
                        switch (tipoCliente) {
                            case REGULAR:
                                return new ClienteRegular(partes[0], cpf, senha, tipoCliente);
                            case GOLD:
                                return new ClienteGold(partes[0], cpf, senha, tipoCliente);
                            case VIP:
                                return new ClienteVIP(partes[0], cpf, senha, tipoCliente);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return null; // Retorna null se não encontrar o cliente
    }

    public static List<Conta> lerContasDoArquivo() {
        List<Conta> contas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("contas.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 3) {
                    String cpf = partes[0];
                    TipoConta tipoConta = TipoConta.valueOf(partes[1].toUpperCase()); // Converte para enum
                    double saldo = Double.parseDouble(partes[2]);

                    // Crie a instância apropriada de Conta com base no tipoConta e adicione à lista
                    // de contas
                    switch (tipoConta) {
                        case CORRENTE:
                            contas.add(new ContaCorrente(cpf, saldo));
                            break;
                        case POUPANCA:
                            contas.add(new Poupanca(cpf, saldo));
                            break;
                        case RENDAFIXA:
                            contas.add(new RendaFixa(cpf, saldo));
                            break;
                        case INVESTIMENTO:
                            contas.add(new Investimento(cpf, saldo));
                            break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de contas: " + e.getMessage());
        }
        return contas;
    }

}