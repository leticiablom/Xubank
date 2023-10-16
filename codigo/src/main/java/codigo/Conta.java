package codigo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Conta implements ContaInterface {
    private double saldo;
    private Cliente cliente;
    private List<String> extrato;

    public abstract String getTipoConta();

    public Conta(Cliente cliente) {
        this.cliente = cliente;
        this.saldo = 0.0;
        this.extrato = new ArrayList<>();
    }

    public Conta(String cpf, double saldo2) {
        this.saldo = 0.0;
        this.extrato = new ArrayList<>();
    }

    @Override
    public void depositar(double valor) {
        saldo += valor;
        registrarTransacao("Depósito: +" + valor);
    }

    @Override
    public void sacar(double valor) {
        saldo -= valor;
        registrarTransacao("Saque: -" + valor);
    }
    

    @Override
    public void registrarTransacao(String transacao) {
        extrato.add(transacao);
    }

    public abstract void atualizarSaldo();

    public Cliente getCliente() {
        return cliente;
    }

    public List<String> getExtrato() {
        return extrato;
    }

    @Override
    public double getSaldo() {
        return saldo;
    }

    @Override
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Object getCpf() {
        return null;
    }


    public static void adicionarContaAoArquivo(String cpfCliente, Conta conta) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("contas.txt", true))) {
            String tipoConta = conta.getTipoConta().toString();
            writer.write(cpfCliente + "," + tipoConta + "," + conta.getSaldo());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo de contas: " + e.getMessage());
        }
    }

    public static void atualizarSaldoNoArquivoDeposito(String cpfCliente, String tipoConta, double valor) {
        // Lista temporária para armazenar as linhas lidas do arquivo
        List<String> linhas = new ArrayList<>();

        // Caminho do arquivo de contas
        String arquivoContas = "contas.txt";

        // Ler as linhas do arquivo e encontrar a conta correspondente
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoContas))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                String cpf = partes[0].trim();
                String tipo = partes[1].trim();
                double saldo = Double.parseDouble(partes[2].trim());

                // Encontrar a linha correspondente à conta do cliente
                if (cpf.equals(cpfCliente) && tipo.equals(tipoConta)) {
                    // Atualizar o saldo
                    saldo += valor;
                    linhas.add(cpf + "," + tipo + "," + saldo);
                } else {
                    // Manter as outras linhas como estão
                    linhas.add(linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de contas: " + e.getMessage());
            return;
        }
            // Escrever as linhas atualizadas de volta no arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoContas))) {
            for (String linha : linhas) {
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo de contas: " + e.getMessage());
        }
    }

        public static void atualizarSaldoNoArquivoSaque(String cpfCliente, String tipoConta, double valor) {
        // Lista temporária para armazenar as linhas lidas do arquivo
        List<String> linhas = new ArrayList<>();

        // Caminho do arquivo de contas
        String arquivoContas = "contas.txt";

        // Ler as linhas do arquivo e encontrar a conta correspondente
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoContas))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                String cpf = partes[0].trim();
                String tipo = partes[1].trim();
                double saldo = Double.parseDouble(partes[2].trim());

                // Encontrar a linha correspondente à conta do cliente
                if (cpf.equals(cpfCliente) && tipo.equals(tipoConta)) {
                    // Atualizar o saldo
                    saldo -= valor;
                    linhas.add(cpf + "," + tipo + "," + saldo);
                } else {
                    // Manter as outras linhas como estão
                    linhas.add(linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de contas: " + e.getMessage());
            return;
        }

        // Escrever as linhas atualizadas de volta no arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoContas))) {
            for (String linha : linhas) {
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo de contas: " + e.getMessage());
        }
    }


}