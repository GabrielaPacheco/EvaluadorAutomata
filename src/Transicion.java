public class Transicion {
    private Estado origen, destino;
    private Character simbolo;


    // Constructor con todos los parámetros
    public Transicion(Estado origen, Estado destino, Character simbolo) {
        this.origen = origen;
        this.destino = destino;
        this.simbolo = simbolo;
    }

    // Getters
    public Estado getOrigen() {
        return origen;
    }

    public Estado getDestino() {
        return destino;
    }

    public Character getSimbolo() {
        return simbolo;
    }


}
