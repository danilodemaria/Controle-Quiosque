/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package back;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TeclaAtalho extends JFrame {

    public TeclaAtalho(JButton cadastrarButton) {

        //ActionListener
        cadastrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonAction(e);
            }
        });

        //KeyListener para o Frame
        cadastrarButton.addKeyListener(new KeyListener() {

            //Quando soltar a tecla
            public void keyReleased(KeyEvent e) {

                //Se a tecla pressionada for igual a F2
                if (e.getKeyCode() == KeyEvent.VK_F1) {
                    buttonAction(e);
                } else if (e.getKeyCode() == KeyEvent.VK_F2) {
                    lanca();
                } else if (e.getKeyCode() == KeyEvent.VK_F3) {
                    try {
                        exibe();
                    } catch (SQLException ex) {
                        Logger.getLogger(TeclaAtalho.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_F4) {
                    relatorio();
                } else if (e.getKeyCode() == KeyEvent.VK_F5) {
                    relatorioProduto1();
                } else if (e.getKeyCode() == KeyEvent.VK_F6) {
                    int opcao = JOptionPane.showConfirmDialog(null, "Deseja realmente sair?");
                    if (opcao == 0) {
                        System.exit(0);
                    }

                }
            }

            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
            }
        });

    }

    //Tanto a tecla de atalho quanto o click no botão
    //vai chamar esse método
    private void buttonAction(AWTEvent e) {
        if (e instanceof KeyEvent) {
            cadastrar a = new cadastrar();
            a.setVisible(true);
        }
    }

    private void lanca() {
        lancamento b = new lancamento();
        b.setVisible(true);
    }

    public void exibe() throws SQLException {
        exibeProdutos c = new exibeProdutos();
        c.setVisible(true);
    }

    public void relatorio() {
        relatorio d = new relatorio();
        d.setVisible(true);
    }

    public void relatorioProduto1() {
        relatorioProduto e = new relatorioProduto();
        e.setVisible(true);
    }
}
