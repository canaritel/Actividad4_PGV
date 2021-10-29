package pratica4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    static int MAX_LIMITE; //máxima conexiones simultáneas
    static int MAX_USUARIOS; //usuarios permitidas
    static int contador = 0;
    static private Usuario usuario;

    public static void main(String[] args) {

        InputStreamReader stream = new InputStreamReader(System.in, Charset.forName("UTF-8"));
        BufferedReader bf = new BufferedReader(stream);

        int entrada = 0;

        System.out.println("*****************************************************************");
        System.out.println("*******        SEMÁFOROS CON HILOS COMPETIDORES        **********");
        System.out.println("*****************************************************************");

        do {
            System.out.println("¿Qué número de usuarios desea para este test? (Por ejemplo 100) ");
            String line = "";
            try {
                line = bf.readLine();
                entrada = Integer.parseInt(line);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            } catch (NumberFormatException ex) {
                System.err.println("Introduzca números enteros superiores a 0");
                entrada = 0;
            }
        } while (entrada <= 0 || entrada > 20000);

        MAX_USUARIOS = entrada;
        entrada = 0;

        do {
            System.out.println("¿Número máximo de sesiones simultáneas? (Por ejemplo 10) ");
            String line = "";
            try {
                line = bf.readLine();
                entrada = Integer.parseInt(line);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            } catch (NumberFormatException ex) {
                System.err.println("Introduzca números enteros superiores a 0");
                entrada = 0;
            }
        } while (entrada <= 0 || entrada > 500);

        MAX_LIMITE = entrada;
        //establecemos en los servidores los hilos máximos a ejecutarse, lo que son las sesiones simultáneas
        Servidores servidor = new Servidores(MAX_LIMITE); //este servidor ejecuta los hilos de reservsas y pagos
        Servidores3 servidor3 = new Servidores3(MAX_LIMITE); //este servidor ejecuta el hilo de postventa

        ArrayList<Usuario> listaUsuarios = new ArrayList<>(); //creamos los nombres de los usuarios
        listaUsuarios = crearUsuario(MAX_USUARIOS);

        ReservaEntrada[] reserva = new ReservaEntrada[MAX_USUARIOS];
        PasarelaPago[] pasarela = new PasarelaPago[MAX_USUARIOS];
        Postventa[] postventa = new Postventa[MAX_USUARIOS];

        //Procedemos al hilo de reservas
        for (int i = 0; i < MAX_USUARIOS; i++) {
            reserva[i] = new ReservaEntrada(servidor, listaUsuarios.get(i));
            reserva[i].start();
        }

        //Procedemos al hilo de pagos
        for (int i = 0; i < MAX_USUARIOS; i++) {
            pasarela[i] = new PasarelaPago(servidor, listaUsuarios.get(i));
            pasarela[i].start();
        }

         //Procedemos al hilo de postventa
        for (int i = 0; i < MAX_USUARIOS; i++) {
            postventa[i] = new Postventa(servidor3, listaUsuarios.get(i));
            try {
                reserva[i].join(); //esperamos hilo especificado esté muerto(ejecución terminada)
                pasarela[i].join(); //esperamos hilo especificado esté muerto(ejecución terminada)
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            postventa[i].start(); 
        }

    }

    static private String crearNombre() {
        contador++;
        return "Usuario" + contador;
    }

    static private ArrayList<Usuario> crearUsuario(int MAX) {
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        for (int i = 0; i < MAX; i++) {
            usuario = new Usuario(crearNombre());
            listaUsuarios.add(usuario);
        }
        return listaUsuarios;
    }

}
