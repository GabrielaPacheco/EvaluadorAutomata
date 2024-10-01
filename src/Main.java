import java.util.*;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


// Crear estados

        Scanner scanner = new Scanner(System.in);

        // Introducción
        System.out.println("Bienvenido al evaluador de autómatas finitos no deterministas con transiciones ε.");
        System.out.println("Ingrese los símbolos del alfabeto, estados, estados finales y transiciones.");

        // Leer símbolos del alfabeto
        System.out.print("Ingrese los símbolos del alfabeto (separados por comas): ");
        String[] simbolosInput = scanner.nextLine().split(",");
        Set<Character> simbolos = new HashSet<>();

        // Se crea un conjunto de caracteres para almacenar los símbolos del alfabeto
        for (String simbolo : simbolosInput) {
            simbolos.add(simbolo.trim().charAt(0));
        }

        // Leer estados
        System.out.print("Ingrese los estados (separados por comas): ");
        String[] estadosInput = scanner.nextLine().split(",");
        List<Estado> estados = new ArrayList<>();

        // Leer estados finales
        System.out.print("Ingrese los estados finales (separados por comas): ");

        String[] finalesInput = scanner.nextLine().split(",");

        List<String> estadosFinales = List.of(finalesInput);

        // Crear estados
        for (String nombre : estadosInput) {
            // Se crea un booleano para indicar si el estado actual es fin
            boolean esFinal = estadosFinales.contains(nombre.trim());

            // Se verifica si el estado actual es el primero de la lista.
            // Si es así, se marca como inicial.
            boolean esInicial = estados.size() == 0;

            // Se crea un nuevo objeto de tipo Estado con el nombre, el indicador de si es final
            // y el indicador de si es inicial
            estados.add(new Estado(nombre.trim(), esInicial, esFinal));
        }

        // Leer transiciones
        List<Transicion> transiciones = new ArrayList<>();
        String transicionInput;
        System.out.println("Ingrese las transiciones (origen,símbolo,destino), una por línea. Escriba 'fin' para terminar:");

        while (true) {
            // Lee una línea de entrada, elimina espacios en blanco al inicio y final, y la almacena en transicionInput
            transicionInput = scanner.nextLine().trim();

            // Si la entrada es "fin", se termina el bucle
            if (transicionInput.equals("fin")) {
                break;
            }

// Divide la entrada en tres partes (origen, símbolo, destino) separadas por comas
            String[] partes = transicionInput.split(",");

            // Verifica si se ingresaron tres partes, como se espera para una transición
            if (partes.length != 3) {
                System.out.println("Error: La transición '" + transicionInput + "' no tiene el formato correcto.");
                continue;
            }

            // Extrae el origen, símbolo y destino de la transición
            String origen = partes[0].trim();
            Character simbolo = partes[1].trim().equals("ε") ? null : partes[1].trim().charAt(0);
            String destino = partes[2].trim();

            // Busca los estados de origen y destino en la lista de estados
            Estado estadoOrigen = estados.stream().filter(e -> e.getNombre().equals(origen)).findFirst().orElse(null);
            Estado estadoDestino = estados.stream().filter(e -> e.getNombre().equals(destino)).findFirst().orElse(null);

            // Si ambos estados se encuentran, se crea una nueva transición y se agrega a los estados correspondientes
            if (estadoOrigen != null && estadoDestino != null) {
                Transicion nuevaTransicion = new Transicion(estadoOrigen, estadoDestino, simbolo);
                estadoOrigen.agregarTransicion(nuevaTransicion);
                transiciones.add(nuevaTransicion);
            }
            else {
                // Si alguno de los estados no se encuentra, se muestra un mensaje de error
                System.out.println("Error: Estado de origen o destino no definido.");
            }
        }

        // Crear el autómata
        Estado estadoInicial = estados.stream().filter(Estado::isInicial).findFirst().orElse(null);
        Evaluador automata = new Evaluador(estadoInicial);

        // Evaluar múltiples palabras
        String continuar;
        do {
            System.out.print("Ingrese una palabra para evaluar (o 'q' para salir): ");
            String palabra = scanner.nextLine();

            // Si el usuario ingresa 'q', se sale del bucle
            if (palabra.equals("q")) break;

            // Evalúa la palabra ingresada utilizando el autómata
            boolean resultado = automata.evaluar(palabra);

            // Imprime el resultado de la evaluación
            System.out.println(resultado ? "La palabra satisface la expresión regular." : "La palabra no satisface la expresión regular.");

            // Pregunta al usuario si desea evaluar otra palabra
            System.out.print("¿Desea evaluar otra palabra? (s/n): ");
            continuar = scanner.nextLine().trim().toLowerCase();

        } while (continuar.equals("s")); // El bucle continúa mientras el usuario ingrese 's'

        scanner.close();

    }
}