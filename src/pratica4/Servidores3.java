package pratica4;

import static java.lang.Thread.sleep;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Semaphore;

public class Servidores3 {

    private final int MAX_USERS; // Máximas conexiones simultáneas
    private final Semaphore semaforo;
    private final Semaphore mutex = new Semaphore(1);

    public Servidores3(int MAX_USERS) {
        this.MAX_USERS = MAX_USERS;
        this.semaforo = new Semaphore(MAX_USERS);
    }

    public void postventa(Usuario usuario) {

        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        try {
            semaforo.acquire();
            mutex.acquire();
            int tiempo = (int) (Math.random() * (21 - 1) + 1); //tiempo de espera
            System.out.println(dtf2.format(LocalDateTime.now()) + " | 3. --->" + usuario.getNombre_usuario() + " está entrando en el servicio de Postventa...");
            usuario.setEntrada(3); //paso el estado de entrada a 3
            mutex.release();
            //sleep(1000 * tiempo); //formula real con espera del tiempo calculado
            sleep(1000); //para no esperar tanto en ver los resultados
            System.out.println(dtf2.format(LocalDateTime.now()) + " | 3. <---" + usuario.getNombre_usuario() + " está saliendo del servicio de Postventa."
                    + " | Tiempo empleado en este proceso = " + tiempo + " segundos");

        } catch (InterruptedException ex) {
            System.err.println("Error " + ex.getLocalizedMessage());
        } finally {
            semaforo.release();
        }

    }

}
