package codigo;
public class ContaCorrente extends Conta {

    private static final double TAXA_MENSAL = 20.0;
    private static final double LIMITE_ESPECIAL = 200.0;

    public ContaCorrente(Cliente cliente) {
        super(cliente);
    }
    
    public ContaCorrente(String cpf, double saldo) {
        super(cpf, saldo);
    }
    

    @Override
    public String getTipoConta() {
        return "CORRENTE";
    }
    
    @Override
    public void atualizarSaldo() {
        double saldoAnterior = getSaldo();
        double novoSaldo = saldoAnterior - TAXA_MENSAL;
        if (novoSaldo < -LIMITE_ESPECIAL) {
            novoSaldo = -LIMITE_ESPECIAL;
        }
        setSaldo(novoSaldo);
        registrarTransacao("Taxa mensal: -" + TAXA_MENSAL);
    }
}