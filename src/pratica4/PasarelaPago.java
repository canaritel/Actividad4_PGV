package pratica4;
//

public class PasarelaPago extends Thread {

    private final Servidores servidor;
    private final Usuario usuario;

    public PasarelaPago(Servidores servidor, Usuario usuario) {
        this.servidor = servidor;
        this.usuario = usuario;
    }

    @Override
    public void run() {
        servidor.pagar(usuario);
    }
}
