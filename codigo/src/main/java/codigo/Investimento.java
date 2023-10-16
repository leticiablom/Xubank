package codigo;

public class Investimento extends Conta {

    private static final double TAXA_IMPOSTO = 0.15;

    public Investimento(Cliente cliente) {
        super(cliente);
    }

    public Investimento(String cpf, double saldo) {
        super(cpf, saldo);
    }
    
    @Override
    public String getTipoConta() {
        return "INVESTIMENTO";
    }

    @Override
    public void atualizarSaldo() {
        // Implementação do cálculo do saldo com rendimento diário (positivo ou negativo)
        double saldoAnterior = getSaldo();
        double rendimentoDiario = Math.random() * 0.02 - 0.01; // Rendimento aleatório entre -1% e +1%
        double rendimento = saldoAnterior * rendimentoDiario;
        double novoSaldo = saldoAnterior + rendimento;
        setSaldo(novoSaldo);
        registrarTransacao("Rendimento diário: +" + rendimento);
    }

    @Override
    public void sacar(double valor) {
        double saldoAnterior = getSaldo();
        if (valor > saldoAnterior) {
            System.out.println("Saldo insuficiente para saque!");
        } else {
            double imposto = valor * TAXA_IMPOSTO;
            double novoSaldo = saldoAnterior - valor - imposto;
            setSaldo(novoSaldo);
            registrarTransacao("Saque: -" + valor + ", Imposto: -" + imposto);
        }
    }
}