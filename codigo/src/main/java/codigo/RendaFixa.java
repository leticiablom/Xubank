package codigo;

public class RendaFixa extends Conta {

    private double taxaRendimento;
    private static final double IMPOSTO_SAQUE = 0.15;

    public RendaFixa(Cliente cliente, double taxaRendimento) {
        super(cliente);
        this.taxaRendimento = taxaRendimento;
    }
    
    public RendaFixa(String cpf, double saldo) {
        super(cpf, saldo);
    }

    @Override
    public String getTipoConta() {
        return "RENDAFIXA";
    }

    @Override
    public void atualizarSaldo() {
        // Implementação do cálculo do saldo com rendimento mensal
        double saldoAnterior = getSaldo();
        double rendimento = saldoAnterior * taxaRendimento;
        double novoSaldo = saldoAnterior + rendimento;
        setSaldo(novoSaldo);
        registrarTransacao("Rendimento mensal: +" + rendimento);
    }

    @Override
    public void sacar(double valor) {
        double saldoAnterior = getSaldo();
        if (valor > saldoAnterior) {
            System.out.println("Saldo insuficiente para saque!");
        } else {
            double imposto = valor * IMPOSTO_SAQUE;
            double novoSaldo = saldoAnterior - valor - imposto;
            setSaldo(novoSaldo);
            registrarTransacao("Saque: -" + valor + ", Imposto: -" + imposto);
        }
    }
}