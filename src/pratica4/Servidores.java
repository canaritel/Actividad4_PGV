package pratica4;
//
import static java.lang.Thread.sleep;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Semaphore;

public class Servidores {

    private final long MAX_USERS; // Máximas conexiones simultáneas
    private final Semaphore semaforoReservas;
    private final Semaphore semaforoPagos = new Semaphore(0);
    private final Semaphore mutex = new Semaphore(1);

    public Servidores(int MAX_USERS) {
        this.MAX_USERS = MAX_USERS;
        this.semaforoReservas = new Semaphore(MAX_USERS);
    }

    public void reservar(Usuario usuario) {

        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        try {
            semaforoReservas.acquire();
            mutex.acquire();
            int tiempo = (int) (Math.random() * (21 - 5) + 5); //tiempo de espera
            System.out.println(dtf2.format(LocalDateTime.now()) + " | 1. ->" + usuario.getNombre_usuario() + " está entrando a la seleción de butaca...");
            usuario.setEntrada(1); //paso el estado de entrada a 1
            mutex.release();
            //sleep(1000 * tiempo); //formula real con espera del tiempo calculado
            sleep(1000); //para no esperar tanto en ver los resultados
            System.out.println(dtf2.format(LocalDateTime.now()) + " | 1. <-" + usuario.getNombre_usuario() + " está saliendo de reserva de entrada."
                    + " | Tiempo empleado en este proceso = " + tiempo + " segundos");

        } catch (InterruptedException ex) {
            System.err.println("Error " + ex.getLocalizedMessage());
        } finally {
            semaforoPagos.release();
        }
    }

    public void pagar(Usuario usuario) {

        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        try {
            semaforoPagos.acquire();
            mutex.acquire();
            int tiempo = (int) (Math.random() * (11 - 1) + 1); //tiempo de espera
            System.out.println(dtf2.format(LocalDateTime.now()) + " | 2. -->" + usuario.getNombre_usuario() + " está entrando al pago de la entrada");
            usuario.setEntrada(2); //paso el estado de entrada a 2
            System.out.println(dtf2.format(LocalDateTime.now()) + " | 2. -->" + usuario.getNombre_usuario() + " ha pagado la entrada!!.");
            mutex.release();
            //sleep(1000 * tiempo); //formula real con espera del tiempo calculado
            sleep(1000); //para no esperar tanto en ver los resultados
            System.out.println(dtf2.format(LocalDateTime.now()) + " | 2. <--" + usuario.getNombre_usuario() + " está saliendo de la plataforma de pago."
                    + " | Tiempo empleado en este proceso = " + tiempo + " segundos");

        } catch (InterruptedException ex) {
            System.err.println("Error " + ex.getLocalizedMessage());
        } finally {
            semaforoReservas.release();
        }
    }

}
