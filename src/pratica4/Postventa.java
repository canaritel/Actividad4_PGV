package pratica4;
//
public class Postventa extends Thread {

    private final Servidores3 servidor;
    private final Usuario usuario;

    public Postventa(Servidores3 servidor, Usuario usuario) {
        this.servidor = servidor;
        this.usuario = usuario;
    }

    @Override
    public void run() {
        servidor.postventa(usuario);
    }
}
