package calculadora;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Interfaz de la calculadora científica
 *
 * @author Cristian Camilo Echeverri Git: cristianecheverri
 *
 */
public class FrmCalculadora extends JFrame {

    private static final long serialVersionUID = 1L;
    private static JTextArea txtExpresion; // Recibe la expresión para operar
    private static JTextField txtSalida; // Muestra el resultado de la operación
    private static String expresion; // Almacena la expresión ingresada
    private JPanel panelBotonesBasicos, panelFunciones, panelBotones, panelEntradaSalida;
    private JButton[] botonesBasicos, botonesFunciones; // Arrays de botones
    private JButton botonPulsado; // Almacena el botón pulsado

    public FrmCalculadora() {
        setTitle("Calculadora científica");

        add(getPanelEntradaSalida(), BorderLayout.NORTH);
        add(getPanelBotones(), BorderLayout.CENTER);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(660, 415));
        setResizable(false);
        pack();
        setVisible(true);
    }

    /**
     * Crea el panel que contiene los campos de texto que reciben y muestran los
     * resultados numericos
     *
     * @return panelEntradaSalida con dos campos de texto
     */
    private JPanel getPanelEntradaSalida() {
        panelEntradaSalida = new JPanel(new BorderLayout());

        txtExpresion = new JTextArea(3, 45);
        txtExpresion.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txtExpresion.setFont(new Font("Tahoma", 1, 15));

        txtSalida = new JTextField();
        txtSalida.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txtSalida.setFont(new Font("Tahoma", 1, 15));
        txtSalida.setEditable(false);
        txtSalida.setHorizontalAlignment(JLabel.RIGHT);

        panelEntradaSalida.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panelEntradaSalida.add(txtExpresion, BorderLayout.NORTH);
        panelEntradaSalida.add(txtSalida, BorderLayout.SOUTH);

        return panelEntradaSalida;
    }

    /**
     * Crea un panel contenedor de los paneles de los botones
     *
     * @return panelBotones panel que contiene los dos paneles con los botones
     */
    private JPanel getPanelBotones() {
        panelBotones = new JPanel(new BorderLayout(5, 5));
        panelBotones.add(getPanelFunciones(), BorderLayout.NORTH);
        panelBotones.add(getPanelBotonesBasicos(), BorderLayout.PAGE_END);
        return panelBotones;
    }

    /**
     * Crea el panel que contiene los botones con las operaciones básicas de la
     * calculadora
     *
     * @return panelBotonesBasicos panel con los botones basicos
     */
    private JPanel getPanelBotonesBasicos() {
        GridLayout layout = new GridLayout(4, 5, 10, 10);
        panelBotonesBasicos = new JPanel(layout);

        botonesBasicos = new JButton[20];

        String[] botones = new String[]{"7", "8", "9", "DEL", "AC", "4", "5", "6", "*", "/", "1", "2", "3", "+", "-",
            "0", ".", "^", "%", "="}; // Texto de los botones de operaciones basicas

        for (int i = 0; i < botonesBasicos.length; i++) {
            botonesBasicos[i] = new JButton(botones[i]);
            botonesBasicos[i].setForeground(Color.WHITE);

            if (botonesBasicos[i].getText().equals("DEL") || botonesBasicos[i].getText().equals("AC")) {
                botonesBasicos[i].setBackground(new Color(120, 31, 25));
            } else {
                botonesBasicos[i].setBackground(new Color(20, 20, 20));
            }

            botonesBasicos[i].addActionListener((ActionEvent evento) -> {
                botonPulsado = (JButton) evento.getSource(); // Almacena el boton que fue pulsado
                evaluarBotonPulsado(); // Evalúa el botón que se pulsó
            });
            panelBotonesBasicos.add(botonesBasicos[i]);
        }
        panelBotonesBasicos.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return panelBotonesBasicos;
    }

    /**
     * Crea el panel que contiene los botones con las funciones de la
     * calculadora
     *
     * @return panelFunciones panel que contiene los botones de las funciones
     */
    private JPanel getPanelFunciones() {

        GridLayout layout = new GridLayout(4, 5, 10, 10);
        panelFunciones = new JPanel(layout);

        botonesFunciones = new JButton[20];

        String[] botones = new String[]{"RAD", "log", "n!", "1/x", "PI", "ARCSIN", "ARCOS", "ARCTAN", "X^y", "10^x",
            "SIN", "COS", "TAN", "X^2", "X^(1/y)", "(", ")", "X^3", "e", "Abs"}; // Texto de los botones de
        // funciones

        for (int i = 0; i < botonesFunciones.length; i++) {
            botonesFunciones[i] = new JButton(botones[i]);
            botonesFunciones[i].setBackground(new Color(40, 40, 40));
            botonesFunciones[i].setForeground(Color.WHITE);

            botonesFunciones[i].addActionListener((ActionEvent evento) -> {
                botonPulsado = (JButton) evento.getSource();

                evaluarBotonPulsado();
            });
            panelFunciones.add(botonesFunciones[i]);
        }
        panelFunciones.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return panelFunciones;
    }

    /**
     * Evalua el botón que se pulsó y asigna texto al txtExpresion o hace
     * operaciones
     */
    private void evaluarBotonPulsado() {
        String cadena, temporal;
        switch (botonPulsado.getText()) {
            case "DEL":
                cadena = txtExpresion.getText();
                if (cadena.length() > 0) {
                    cadena = cadena.substring(0, cadena.length() - 1);
                    txtExpresion.setText(cadena);
                }
                break;
            case "AC":
                txtExpresion.setText("");
                break;
            case "=":
                AnalizadorLexico.positive = 0;
                Prueba();
                break;
            case "X^2":
                temporal = txtExpresion.getText();
                txtExpresion.setText(temporal + "^2");
                break;
            case "X^y":
                temporal = txtExpresion.getText();
                txtExpresion.setText(temporal + "^");
                break;
            case "X^(1/y)":
                temporal = txtExpresion.getText();
                txtExpresion.setText(temporal + "^(1/");
                break;
            case "10^x":
                temporal = txtExpresion.getText();
                txtExpresion.setText(temporal + "10^");
                break;
            case "1/x":
                temporal = txtExpresion.getText();
                txtExpresion.setText(temporal + "1/");
                break;
            case "DEG":
                try {
                    temporal = txtSalida.getText();
                    txtSalida.setText("" + Math.toRadians(Double.parseDouble((temporal))));
                    botonPulsado.setText("RAD");
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage() + "\nNo se puede realizar la acción");
                }
                break;
            case "RAD":
                try {
                    temporal = txtSalida.getText();
                    txtSalida.setText("" + Math.toDegrees(Double.parseDouble((temporal))));
                    botonPulsado.setText("DEG");
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage() + "\nNo se puede realizar la acción");
                }
                break;
            case "X^3":
                temporal = txtExpresion.getText();
                txtExpresion.setText(temporal + "^3");
                break;
            default:
                temporal = txtExpresion.getText();
                txtExpresion.setText(temporal + botonPulsado.getText());
        }
    }

    /**
     * Utiliza el analizador lexico y el convertidor a posfijo para evaluar la
     * expresión y dar un resultado
     */
    public void Prueba() {
        expresion = txtExpresion.getText();

        AnalizadorLexico analizador = new AnalizadorLexico(expresion);
        analizador.normalizarExpresion();
        if (AnalizadorLexico.positive != AnalizadorLexico.expresionEntrada.length()) {
            JOptionPane.showMessageDialog(null, "Syntax error", "Error", JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("---------------------- NUEVA ------------------------");
            System.out.println("Expresion que ingresa: " + expresion);
            System.out.print("Expresion normalizada: ");
            analizador.imprimir();
            System.out.println("\nTamaño: " + analizador.getListSimbolos().size());

            Posfijo.getPosfijo(analizador.getListSimbolos());
            ArrayList<String> expresionPosfija = Posfijo.getPosfijo(analizador.getListSimbolos());

            System.out.println("\nEmpresion posfija: " + expresionPosfija.toString());

            txtSalida.setText("" + Posfijo.evalPosfijo(expresionPosfija));

            System.out.println("Resultado: " + Posfijo.evalPosfijo(expresionPosfija));

            System.out.println();
            System.out.println();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new FrmCalculadora().setVisible(true);
        });
    }
}
