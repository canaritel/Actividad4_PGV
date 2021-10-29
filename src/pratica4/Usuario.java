package pratica4;

public class Usuario {

    private String nombre_usuario;
    private int entrada; //indica el tipo de entradas que tiene | 0 sin asignar | 1 reservado | 2 pagado | 3 postventa

    public Usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
        this.entrada = 0;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public int getEntrada() {
        return entrada;
    }

    public void setEntrada(int entrada) {
        this.entrada = entrada;
    }

    @Override
    public String toString() {
        return nombre_usuario;
    }

}
