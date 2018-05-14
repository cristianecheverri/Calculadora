package calculadora;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.Stack;

/**
 * Esta clase implementa dos métodos principales y sus auxiliares: 
 * - getPosfijo, que convierte una expresión dada en infijo a posfijo y 
 * - evalPosfijo, que evalúa una expresión en posfijo
 *
 * @authors Carlos Cuesta / Cristian Echeverri 
 * Git: cristianecheverri 
 * Tercer proyecto Estructuras de datos 
 * Ingeniería en informática 
 * Tercer semestre 
 * Universidad de Caldas - Colombia 
 * Docente: Carlos Cuesta Iglesias
 */

/*
 * Algoritmo de infijo a posfijo (RPN: Reverse Polish Notation):
 * 1. Recorra la expresión infija de izquierda a derecha. 
 * 2. Si la subcadena actual es un operando, envíelo a la salida en este caso un ArrayList. 
 * 3. De lo contrario... 
 * 3.1  Si la precedencia del operador actual es mayor que la precedencia del operador en la pila (o la pila está vacía), agréguela a la pila... 
 * 3.2  De lo contrario, saque operadores de la pila hasta que la precedencia del operador leído sea menor o igual a la precedencia del operador de la parte superior de la pila. Envíe el operador actual de la expresión a la pila. 
 * 4. Si la subcadena actual es un '(', agréguela a la pila. 
 * 5. Si la subcadena actual es ')', sáquela de la pila y continúe sacando de la pila y enviando a la salida hasta encontrar '('. 
 * 6. Repita los pasos 2 a 6 hasta que termine la expresión infija. 
 * 7. Mientras la pila no esté vacía, saque de la pila y envíe a la salida.
 *
 *
 * Algoritmo para evaluar un expresión en posfijo:
 *
 * Para cada elemento de la expresión RPN:
 *  1.  Tomar un símbolo de la expresión RPN.
 *  2.  Si el símbolo es un operando colocarlo en la pila.
 *  3.  Si el símbolo es un operador entonces tomar los dos valores del tope de la pila, aplicar el operador
 *      y colocar el resultado en el nuevo tope de la pila. (Se produce un error en caso de no tener los 2 valores)
 * 
 * Importante: no elimite los comentarios de esta clase para que pueda recordar más fácilmente cómo está implementada
 */
public class Posfijo {

    private static int posicion;

    /**
     * Evalúa un símbolo recibido como argumento para determinar si está dentro
     * de los operadores permitidos.
     *
     * @param simbolo un String que se supone representa un operador.
     * @return true si el símbolo evaluado es un operador o paréntesis
     */
    private static boolean esOperador(String simbolo) {
        return "+-*/^()%".contains(simbolo);
    }

    /**
     * Evalúa un símbolo recibido como argumento para determinar si corresponde
     * a un valor real.
     *
     * @param simbolo un String que se supone representa un valor real.
     * @return true si el símbolo evaluado corresponde a un valor real.
     */
    private static boolean esOperando(String simbolo) {
        try {
            // se generará un error de tipo NumberFormatException si no es un valor
            Double.parseDouble(simbolo);
            return true;
        } catch (java.lang.NumberFormatException e) {
            return false;
        }
    }

    /**
     * Evalúa un simbolo recibido como argumento para determinar si es una de
     * las funciones permitidas
     *
     * @param simbolo es un String que se supone contiene los simbolos de la
     * función
     * @return true si el simbolo evaluado es una de las funciones permitidas
     */
    private static boolean esFuncion(String simbolo) {
        String[] func = new String[]{"SIN", "COS", "TAN", "log", "PI", "ARCSIN", "ARCOS", "ARCTAN", "e", "Abs", "n!"};

        for (String string : func) {
            if (string.equals(simbolo)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Evalúa un símbolo recibido como argumento para determinar si es un
     * operador válido y en caso de serlo también determinar su precedencia.
     *
     * @param simbolo el String a ser evaluado como operador.
     * @return un valor que representa el orden de precedencia.
     */
    private static int getPrecedencia(String simbolo) {
        int precedencia = -1;

        switch (simbolo) {
            case "+":
            case "-":
                precedencia = 1;
                break;
            case "*":
            case "/":
            case "%":
                precedencia = 2;
                break;
            case "^":
                precedencia = 3;
                break;
        }
        return precedencia;
    }

    /**
     * Devuelve una lista de símbolos que representa la notación posfija de la
     * expresión infija recibida como argumento.
     *
     * @param expresionInfija una lista de símbolos que representa una expresión
     * aritmética en notación infija
     * @return la misma lista de símbolos recibida como argumento, pero en
     * notación RPN
     */
    public static ArrayList<String> getPosfijo(ArrayList<String> expresionInfija) {
        Stack<String> stack = new Stack<>();
        ArrayList<String> expresionEnPosfijo = new ArrayList<>();
        String simbolo;

        for (int i = 0; i < expresionInfija.size(); i++) {
            simbolo = expresionInfija.get(i);

            if (esOperando(simbolo)) {
                expresionEnPosfijo.add(simbolo);
            } else if (simbolo.equals("(")) {
                stack.push(simbolo);
            } else if (simbolo.equals(")")) {
                // mientras haya elementos en la pila y el último elemento de la pila no sea "("
                // agregar a la expresión en posfijo lo que se vaya desapilando
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    expresionEnPosfijo.add(stack.pop());
                }
                // si la pila no está vacía y el último elemento no es "(", hay un error en la expresión
                if (!stack.isEmpty() && !stack.peek().equals("(")) {
                    System.out.println("Error en la expresión");
                    return null;
                } else if (!stack.isEmpty()) { // en caso contrario si la pila no está vacía, sacar el último elemento
                    stack.pop();
                }
            } else if (esOperador(simbolo)) {
                // si la pila no está vacía y la precedencia del símbolo de la expresión <=  la precedencia del símbolo de la pila...
                if (!stack.isEmpty() && getPrecedencia(simbolo) <= getPrecedencia(stack.peek())) {
                    expresionEnPosfijo.add(stack.pop());
                }
                stack.push(simbolo);
            } else if (esFuncion(simbolo)) {

                switch (expresionInfija.get(i)) {
                    case "PI": // Si la funcion es PI...
                        simbolo = "" + resolverFuncion(simbolo, 0);
                        break;
                    case "e": // Si la funcion es el numero de Euler...                    
                        simbolo = "" + resolverFuncion(simbolo, 0);
                        break;
                    default:
                        System.out.println("------ Una funcion -------\n");
                        posicion = i;
                        ArrayList<String> subExpresion = leerExpresionDeParentesis(expresionInfija); //ejmpl: sen(40+5)
                        //Imprimir la expresion que está en el parentesis
                        System.out.println("Expresion del parentesis: " + subExpresion.toString());
                        ArrayList<String> subPosfijo = getPosfijo(subExpresion);
                        //Imprimir la funcion en posfijo
                        System.out.println("Subexpresion en posfijo" + subPosfijo.toString());
                        //Hacer la operacion del parentesis
                        double resultado = evalPosfijo(subPosfijo);
                        //asignar a simbolo el resultado de la funcion
                        simbolo = "" + resolverFuncion(simbolo, resultado);
                        //Avanzar i hasta ")" + 1
                        i = posicion - 1;
                        break;
                }
                expresionEnPosfijo.add(simbolo);
            }
        }
        // mientras la pila no esté vacía, sacar elementos de ellá y agregarlos a la expresión posfija
        while (!stack.isEmpty()) {
            expresionEnPosfijo.add(stack.pop());
        }
        return expresionEnPosfijo;
    }

    /**
     * Halla el factorial del numero que recibe como argumento
     *
     * @param n numero al que se le va a hallar el factorial
     * @return factorial de n
     */
    private static double factorial(double n) {
        return (n == 0) ? 1 : n * factorial(n - 1);
    }

    /**
     * Resuelve la funcion que ingresa por parametros
     *
     * @param simbolo funcion que se va a resolver
     * @param resultado valor dentro del parentesis
     * @return solucion de la resolucion de la funcion
     */
    private static double resolverFuncion(String simbolo, double resultado) {
        double operacion = 0;
        switch (simbolo) {
            case "SIN":
                operacion = Math.sin(resultado);
                break;
            case "COS":
                operacion = Math.cos(resultado);
                break;
            case "TAN":
                operacion = Math.tan(resultado);
                break;
            case "log":
                operacion = Math.log10(resultado);
                break;
            case "PI":
                operacion = Math.PI;
                break;
            case "ARCSIN":
                operacion = Math.asin(resultado);
                break;
            case "ARCOS":
                operacion = Math.acos(resultado);
                break;
            case "ARCTAN":
                operacion = Math.atan(resultado);
                break;
            case "e":
                operacion = Math.E;
                break;
            case "Abs":
                operacion = Math.abs(resultado);
                break;
            case "n!":
                operacion = factorial((double) (resultado));
                break;
        }
        return operacion;
    }

    /**
     * Evalúa la expresión infija y retorna lo que está dentro del parentesis de
     * la función a evaluar
     *
     * @param expresionInfija expresión que se está evaluando
     * @return un ArrayList que contiene la expresión dentro del parentesis de
     * la función
     */
    private static ArrayList<String> leerExpresionDeParentesis(ArrayList<String> expresionInfija) {
        ArrayList<String> subExpresion = new ArrayList<>();

        int i = posicion + 1;
        int parentesis = 0;
        boolean parent = true;

        while (parent) {
            if (expresionInfija.get(i).equals("(")) {
                    parentesis++;
            } else if (expresionInfija.get(i).equals(")")) {
                    parentesis--;
            }
            subExpresion.add(expresionInfija.get(i));
            if (parentesis == 0) {
                    parent = false;
            }
            i++;
        }
        posicion = i;
        return subExpresion;
    }

    /**
     * Realiza la operación de la expresión posfija y retorna un valor si la
     * expresión está correcta
     *
     * @param expresionPosfija la cual se evaluará y resolverá
     * @return resultado de evaluar la expresión posfija
     */
    public static double evalPosfijo(ArrayList<String> expresionPosfija) {
        Stack<Double> stack = new Stack<>();

        for (int i = 0; i < expresionPosfija.size(); i++) {

            String simbolo = expresionPosfija.get(i);

            if (esOperando(simbolo)) {
                // si el actual símbolo de la expresión es un operando, apilarlo
                stack.push(Double.parseDouble(simbolo));
            } else {
                // si el actual símbolo de la expresión es un operador
                // sacar dos operandos de la pila 
                double valorB = stack.pop();
                double valorA = stack.pop();
                double operacion = Double.NaN; //operación inicializada como Not a Number

                // se realiza la operación indicada por símbolo, con los elementos extraídos de la pila
                switch (simbolo) {
                    case "+":
                        operacion = valorA + valorB;
                        break;
                    case "-":
                        operacion = valorA - valorB;
                        break;
                    case "*":
                        operacion = valorA * valorB;
                        break;
                    case "/":
                        operacion = valorA / valorB;
                        break;
                    case "%":
                        operacion = valorA % valorB;
                        break;
                    case "^":
                        operacion = Math.pow(valorA, valorB);
                        break;
                    default:
                        throw new ArithmeticException(String.format("La operación «%s» no está permitida", simbolo));
                }
                // la operación resultante se agrega a la pila
                stack.push(operacion);
            }
        }
        // terminado el proceso se retorna lo que queda en la pila
        return stack.pop();
    }
}
