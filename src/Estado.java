import java.util.ArrayList;
import java.util.List;

public class Estado
{
    private String nombre;
    private boolean inicial, finales;
    private List<Transicion> transiciones;


    // Constructor con todos los parámetros
    public Estado(String nombre, boolean inicial, boolean finales) {
        this.nombre = nombre;
        this.inicial = inicial;
        this.finales = finales;
        this.transiciones = new ArrayList<>();
    }

    public void agregarTransicion(Transicion transicion) {
        transiciones.add(transicion);
    }

    public List<Transicion> getTransiciones() {
        return transiciones;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public boolean isInicial() {
        return inicial;
    }

    public boolean isFinales() {
        return finales;
    }



}
