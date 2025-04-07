import java.util.Random;

import javax.swing.JPanel;

public class Jugador {

    private int TOTAL_CARTAS = 10;
    private int MARGEN = 10;
    private int DISTANCIA = 40;

    private Carta[] cartas = new Carta[TOTAL_CARTAS];
    private Random r = new Random(); 

    public void repartir() {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();

        int posicion = MARGEN + (TOTAL_CARTAS - 1) * DISTANCIA;
        for (Carta carta : cartas) {
            carta.mostrar(pnl, posicion, MARGEN);
            posicion -= DISTANCIA;
        }

        pnl.repaint();
    }

    public String getGrupos() {
        String mensaje = "No se encontraron grupos";

        int[] contadores = new int[NombreCarta.values().length];
        for (Carta carta : cartas) {
            contadores[carta.getNombre().ordinal()]++;
        }

        boolean hayGrupos = false;
        for (int contador : contadores) {
            if (contador >= 2) {
                hayGrupos = true;
                break;
            }
        }

        if (hayGrupos) {
            mensaje = "Se encontraron los siguientes grupos:\n";
            int fila = 0;
            for (int contador : contadores) {
                if (contador >= 2) {
                    mensaje += Grupo.values()[contador] + " de " + NombreCarta.values()[fila] + "\n";
                }
                fila++;
            }
        }

        return mensaje;
    }

    public String getEscaleras() {
        boolean[][] matriz = new boolean[4][13];
    
        for (Carta carta : cartas) {
            int pinta = carta.getPinta().ordinal();
            int valor = carta.getNombre().ordinal();
            matriz[pinta][valor] = true;
        }
    
        StringBuilder resultado = new StringBuilder("Escaleras encontradas:\n");
    
        for (int p = 0; p < 4; p++) {
            int consecutivos = 0;
            int inicio = -1;
            for (int v = 0; v < 13; v++) {
                if (matriz[p][v]) {
                    if (consecutivos == 0) {
                        inicio = v;
                    }
                    consecutivos++;
                    if (consecutivos >= 3) {
                        resultado.append(grupoComoTexto(consecutivos) + " de " + Pinta.values()[p] + " desde " + NombreCarta.values()[inicio] + "\n");
                    }
                } else {
                    consecutivos = 0;
                }
            }
        }
    
        return resultado.toString();
    }
    
    private String grupoComoTexto(int cantidad) {
        return switch (cantidad) {
            case 3 -> "Tercia";
            case 4 -> "Cuarta";
            case 5 -> "Quinta";
            case 6 -> "Sexta";
            case 7 -> "Séptima";
            default -> cantidad + " cartas";
        };
    }
    
    public int calcularPuntaje() {
        int[] contador = new int[13];
        for (Carta carta : cartas) {
            contador[carta.getNombre().ordinal()]++;
        }
    
        int puntaje = 0;
        for (Carta carta : cartas) {
            int repeticiones = contador[carta.getNombre().ordinal()];
            if (repeticiones == 1) { // No está en grupo
                String nombre = carta.getNombre().name();
                if (nombre.equals("AS") || nombre.equals("JACK") || nombre.equals("QUEEN") || nombre.equals("KING")) {
                    puntaje += 10;
                } else {
                    puntaje += carta.getNombre().ordinal() + 1;
                }
            }
        }
    
        return puntaje;
    }
    

}
