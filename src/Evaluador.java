import java.util.*;

class Evaluador {

    private Estado estadoInicial;

    public Evaluador( Estado estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public boolean evaluar(String palabra) {
        // Conjunto de estados actuales, inicialmente contiene solo el estado inicial.
        Set<Estado> estadosActuales = new HashSet<>();
        estadosActuales.add(estadoInicial);

        // Llama a la función auxiliar para evaluar la palabra a partir del estado inicial.
        // Retorna true si la palabra es aceptada por el autómata, false en caso contrario.
        return evaluarDesdeEstados(estadosActuales, palabra);
    }

    private boolean evaluarDesdeEstados(Set<Estado> estadosActuales, String palabra) {
        // Calcular el cierre épsilon de los estados actuales:
        //   - Encuentra todos los estados que se pueden alcanzar desde los estados actuales
        //   - Siguiendo únicamente transiciones épsilon (sin consumir símbolos)
        estadosActuales = calcularCierreEpsilon(estadosActuales);

        // Si la palabra está vacía, verificamos si alguno de los estados actuales es final
            if (palabra.isEmpty()) {
                boolean esAceptada = false;
                for (Estado estado : estadosActuales) {
                    if (estado.isFinales()) {
                        esAceptada = true;
                        break;
                    }
                }
                return esAceptada;
        }

        // Obtener el primer símbolo de la palabra
        char simbolo = palabra.charAt(0);
            // Crear un nuevo conjunto para almacenar los estados alcanzables con símbolo
            Set<Estado> nuevosEstados = new HashSet<>();
            // Iterar sobre cada estado actual
            for (Estado estado : estadosActuales) {
                // Iterar sobre cada transición del estado actual
                for (Transicion transicion : estado.getTransiciones()) {
                    // Si la transición coincide con el símbolo actual de la palabra
                    if (transicion.getSimbolo() != null &&  transicion.getSimbolo()==simbolo) {
                        // Agregar el estado destino de la transición al conjunto de nuevos estados
                        nuevosEstados.add(transicion.getDestino());
                    }
                }
            }
            // Actualizar los estados actuales con los nuevos estados alcanzados
            estadosActuales = nuevosEstados;

        // Llamada recursiva para el resto de la palabra
        return evaluarDesdeEstados(estadosActuales, palabra.substring(1));

    }

    private Set<Estado> calcularCierreEpsilon(Set<Estado> estados) {
        // Conjunto para almacenar los estados alcanzables a través de transiciones épsilon
        Set<Estado> cierreEpsilon = new HashSet<>(estados);

        // Conjunto de estados a explorar, inicialmente igual al conjunto de entrada
        Set<Estado> explorador = new HashSet<>(estados);

        // Mientras haya estados por explorar
        while (!explorador.isEmpty()) {
            // Tomar un estado del conjunto de explorador
            Estado estadoActual = explorador.iterator().next();
            explorador.remove(estadoActual);

            // Explorar las transiciones de este estado
            for (Transicion transicion : estadoActual.getTransiciones()) {
                // Si es una transición épsilon
                if (transicion.getSimbolo() == null) {
                    // Obtener el estado destino de la transición
                    Estado estadoDestino = transicion.getDestino();
                    // Si el estado destino no ha sido visitado aún, agregarlo al cierre épsilon y al explorador
                    if (!cierreEpsilon.contains(estadoDestino)) {
                        cierreEpsilon.add(estadoDestino);
                        explorador.add(estadoDestino);
                    }
                }
            }
        }
        // Retornar el conjunto de estados alcanzables a través de transiciones épsilon
        return cierreEpsilon;
    }
}

