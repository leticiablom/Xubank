package codigo;

public interface ContaInterface {
    void depositar(double valor);
    void sacar(double valor);
    void registrarTransacao(String transacao);
    void atualizarSaldo();
    double getSaldo();
    void setSaldo(double saldo);
}