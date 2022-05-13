package manuel_marin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelMenuPrincipal extends JPanel {
    public PanelMenuPrincipal(VentanaPrincipal ventanaPrincipal) {
        setLayout(null);
        this.ventanaPrincipal = ventanaPrincipal;

        btSeleccionarNivel = new JButton("Selecionar nivel");
        btSeleccionarNivel.setSize(btSeleccionarNivel.getPreferredSize());
        btSeleccionarNivel.setLocation(0, 0);
        btSeleccionarNivel.addActionListener(actionHandler);
        add(btSeleccionarNivel);

        btCrearNivel = new JButton("Crear nivel");
        btCrearNivel.setSize(btCrearNivel.getPreferredSize());
        btCrearNivel.setLocation(150, 0);
        btCrearNivel.addActionListener(actionHandler);
        add(btCrearNivel);
    }

    private class ActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btSeleccionarNivel) {
                ventanaPrincipal.panelMenuJuego = new PanelMenuNiveles(ventanaPrincipal);
                ventanaPrincipal.add(ventanaPrincipal.panelMenuJuego, BorderLayout.CENTER);

                ventanaPrincipal.panelMenuPrincipal.setVisible(false);
                ventanaPrincipal.remove(ventanaPrincipal.panelMenuPrincipal);
            }

            if (e.getSource() == btCrearNivel) {
                ventanaPrincipal.panelCreacionNiveles = new PanelCreacionNiveles(ventanaPrincipal);
                ventanaPrincipal.add(ventanaPrincipal.panelCreacionNiveles, BorderLayout.CENTER);

                ventanaPrincipal.panelMenuPrincipal.setVisible(false);
                ventanaPrincipal.remove(ventanaPrincipal.panelMenuPrincipal);
            }
        }

    }

    JButton btSeleccionarNivel;
    JButton btCrearNivel;
    VentanaPrincipal ventanaPrincipal;
    ActionHandler actionHandler = new ActionHandler();
}
