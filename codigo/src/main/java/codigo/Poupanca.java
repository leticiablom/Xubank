package codigo;

public class Poupanca extends Conta {

    private static final double RENDIMENTO_MENSAL = 0.005; // 0.5% ao mês

    public Poupanca(Cliente cliente) {
        super(cliente);
    }

    public Poupanca(String cpf, double saldo) {
        super(cpf, saldo);
    }

    @Override
    public String getTipoConta() {
        return "POUPANCA";
    }

    @Override
    public void atualizarSaldo() {
        // Implementação do cálculo do saldo com rendimento mensal
        double saldoAnterior = getSaldo();
        double rendimento = saldoAnterior * RENDIMENTO_MENSAL;
        double novoSaldo = saldoAnterior + rendimento;
        setSaldo(novoSaldo);
        registrarTransacao("Rendimento mensal: +" + rendimento);
    }
}