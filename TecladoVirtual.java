import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TecladoVirtual extends JFrame {

    private JTextArea areaTexto;
    private JButton[] teclas;

    public TecladoVirtual() {
        setTitle("Typing Application");
        setSize(1100, 780);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 20, 15, 20));
        painelPrincipal.setBackground(new Color(220, 225, 235));

        JLabel instrucoes = new JLabel(
            "<html>Type some text using your keyboard. The keys you press will be highlighted and the text will be displayed.<br>" +
            "Note: Clicking the buttons with your mouse will not perform any action.</html>"
        );
        instrucoes.setFont(new Font("Arial", Font.PLAIN, 16));
        painelPrincipal.add(instrucoes, BorderLayout.NORTH);

        areaTexto = new JTextArea();
        areaTexto.setFont(new Font("Arial", Font.PLAIN, 20));
        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);
        JScrollPane rolagem = new JScrollPane(areaTexto);
        rolagem.setPreferredSize(new Dimension(1000, 250));
        painelPrincipal.add(rolagem, BorderLayout.CENTER);

        JPanel teclado = new JPanel(new GridLayout(5, 1, 5, 5));
        teclado.setBackground(new Color(220, 225, 235));

        teclas = new JButton[50];
        int indice = 0;

        String[][] linhas = {
            {"~", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "+", "Backspace"},
            {"Tab", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "[", "]", "\\"},
            {"Caps", "A", "S", "D", "F", "G", "H", "J", "K", "L", ";", "'", "Enter"},
            {"Shift", "Z", "X", "C", "V", "B", "N", "M", ",", ".", "/", "↑"},
            {"Espaço", "←", "↓", "→"}
        };

        for (String[] linha : linhas) {
            JPanel painelLinha = new JPanel(new GridLayout(1, linha.length, 5, 5));
            painelLinha.setBackground(new Color(220, 225, 235));

            for (String nome : linha) {
                JButton tecla = new JButton(nome);
                tecla.setFont(new Font("Arial", Font.PLAIN, 16));
                tecla.setFocusPainted(false);
                tecla.setBackground(new Color(245, 245, 248));
                tecla.setBorder(BorderFactory.createLineBorder(new Color(170, 175, 185)));
                tecla.setFocusable(false);
                tecla.setPreferredSize(new Dimension(55, 55));

                if (nome.equals("Espaço")) {
                    painelLinha.add(tecla);
                    painelLinha.add(new JLabel(""));
                } else {
                    painelLinha.add(tecla);
                }

                if (indice < teclas.length) {
                    teclas[indice++] = tecla;
                }
            }
            teclado.add(painelLinha);
        }

        painelPrincipal.add(teclado, BorderLayout.SOUTH);
        add(painelPrincipal, BorderLayout.CENTER);

        areaTexto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                destacarTecla(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                restaurarTeclas();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                areaTexto.requestFocusInWindow();
            }
        });

        setVisible(true);
        areaTexto.requestFocusInWindow();
    }

    private void destacarTecla(KeyEvent e) {
        restaurarTeclas();

        String teclaDigitada = KeyEvent.getKeyText(e.getKeyCode()).toUpperCase();

        for (JButton tecla : teclas) {
            if (tecla != null) {
                String nome = tecla.getText().toUpperCase();

                if (nome.equals(teclaDigitada)
                    || (nome.equals("ESPAÇO") && e.getKeyCode() == KeyEvent.VK_SPACE)
                    || (nome.equals("BACKSPACE") && e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                    || (nome.equals("ENTER") && e.getKeyCode() == KeyEvent.VK_ENTER)
                    || (nome.equals("TAB") && e.getKeyCode() == KeyEvent.VK_TAB)
                    || (nome.equals("SHIFT") && (e.getKeyCode() == KeyEvent.VK_SHIFT))
                    || (nome.equals("CAPS") && e.getKeyCode() == KeyEvent.VK_CAPS_LOCK)) {
                    tecla.setBackground(new Color(120, 175, 245));
                }
            }
        }
    }

    private void restaurarTeclas() {
        for (JButton tecla : teclas) {
            if (tecla != null) {
                tecla.setBackground(new Color(245, 245, 248));
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TecladoVirtual());
    }
}
