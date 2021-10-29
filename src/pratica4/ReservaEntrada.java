package pratica4;

public class ReservaEntrada extends Thread {

    private final Servidores servidor;
    private final Usuario usuario;

    public ReservaEntrada(Servidores servidor, Usuario usuario) {
        this.servidor = servidor;
        this.usuario = usuario;
    }

    @Override
    public void run() {
        servidor.reservar(usuario);
    }

}
