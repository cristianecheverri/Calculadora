package calculadora;

import java.util.ArrayList;

/**
 * Determina si la expresión está bien escrita
 *
 * @author Cristian Camilo Echeverri
 *
 */
public class AnalizadorLexico {

    private final String[] arraySimbolos;
    private final String[] arrayFunciones;
    private final String[] arrayNumeros;
    protected static ArrayList<String> lstSimbolos; //Expresion normalizada
    protected static String expresionEntrada; //Expresion que se va a analizar
    protected static int positive = 0; //Variable que ayuda a determinar si la expresion es correcta

    /**
     * Inicializa las variables
     *
     * @param expresionEntrada contiene la expresión que se va a analizar
     */
    public AnalizadorLexico(String expresionEntrada) {
        arraySimbolos = new String[]{"^", "+", "-", "/", "*", "(", ")", "%"};
        arrayFunciones = new String[]{"S", "I", "N", "C", "O", "T", "A", "l", "o", "g", "P", "R", "e", "b", "s", "n", "!"};
        arrayNumeros = new String[]{".", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        lstSimbolos = new ArrayList<>();
        AnalizadorLexico.expresionEntrada = expresionEntrada;
    }

    /**
     * Devuelve el Array con la expresion normalizada
     *
     * @return expresion normalizada
     */
    public ArrayList<String> getListSimbolos() {
        return lstSimbolos;
    }

    /**
     * Elimina los espacios y las letras que no están definidos dentro del
     * funcionamiento de la calculadora
     */
    public void normalizarExpresion() {
        String temporal = "";

        for (int i = 0; i < expresionEntrada.length(); i++) {

            if (!(expresionEntrada.charAt(i) + "").equals(" ")) { // Si el caracter es distinto a un espacio...
                for (int j = 0; j < arrayNumeros.length; j++) {
                    if ((expresionEntrada.charAt(i) + "").equals(arrayNumeros[j] + "")) { // Si el caracter está contenido en el vector de numeros...
                        temporal += "" + arrayNumeros[j]; // Añade a temporal el numero que está en ese espacio
                        positive++; //incrementa la variable positive
                        break;
                    }
                }
                for (int j = 0; j < arraySimbolos.length; j++) {
                    if ((expresionEntrada.charAt(i) + "").equals(arraySimbolos[j])) { // Si el caracter está contenido en el vector de simbolos...
                        if (!temporal.equals("")) { //Si temporal no está vacío..
                            lstSimbolos.add(temporal); //Agregar a lstSimbolos lo que haya en temporal
                        }
                        lstSimbolos.add(arraySimbolos[j]); //añade el simbolo encontrado
                        temporal = ""; //Borra lo que hay en temporal
                        positive++; //incrementa la variable positive
                        break;
                    }
                }

                for (int j = 0; j < arrayFunciones.length; j++) {
                    if ((expresionEntrada.charAt(i) + "").equals(arrayFunciones[j])) {// Si el caracter está contenido en el vector de funciones...
                        temporal += "" + arrayFunciones[j]; // Añade a temporal el caracter de la funcion que está en ese espacio
                        positive++; //incrementa la variable positive
                        break;
                    }
                }

                if (i == (expresionEntrada.length() - 1)) { // Si i es igual a el tamaño de la expresion de entrada menos uno...
                    if (!temporal.isEmpty()) { // Si temporal no está vacio...
                        lstSimbolos.add(temporal); //Agregar a lstSimbolos lo que haya en temporal
                    }
                    temporal = ""; //eliminar lo que hay en temporal
                }
            }
        }
    }

    /**
     * Imprime la expresion normalizada
     */
    public void imprimir() {
        lstSimbolos.forEach((valor) -> {
            System.out.print(valor);
        });
        positive = 0;
    }

    @Override
    public String toString() {
        return "AnalizadorLexico [lstSimbolos=" + lstSimbolos + "]";
    }
}