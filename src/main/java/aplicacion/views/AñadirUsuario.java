package aplicacion.views;

import aplicacion.Session;
import aplicacion.JdbcUtils;
import aplicacion.dao.UsuarioDAO;
import aplicacion.models.Usuario;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class AñadirUsuario extends JDialog {
    private JPanel panelNewUser;
    private JTextField newUser;
    private JPasswordField newPass;
    private JPasswordField newPass2;
    private JButton botonGuardar;
    private JButton botonCancelar;

    UsuarioDAO usuarioDAO = new UsuarioDAO(JdbcUtils.getConnection());

    public AñadirUsuario(){
        setContentPane(panelNewUser);
        setModal(true);
        setTitle("Nuevo usuario");
        setLocationRelativeTo(null);
        setSize(350,250);
        setResizable(false);

        botonGuardar.addActionListener((e)->{
            validarNuevoUsuario();
        });

        botonCancelar.addActionListener((e)->{
            cancelar();
        });
    }

    private void cancelar() {
        dispose();
        var login = new PantallaLogin();
        login.setVisible(true);
    }

    private void validarNuevoUsuario() {
        Usuario nuevoUsuario = usuarioDAO.validateNewUser(newUser.getText());

        if (nuevoUsuario == null){
            if (Arrays.equals(newPass.getPassword(), newPass2.getPassword())){
                nuevoUsuario = new Usuario();
                nuevoUsuario.setNombre_usuario(newUser.getText());
                String passString = new String(newPass.getPassword());
                nuevoUsuario.setContraseña(passString);

                Session.usuarioSleccionado = nuevoUsuario;
                Session.listadoCopiasDTO = new ArrayList<>();
                Session.usuarioSleccionado.setMicopia(Session.listadoCopiasDTO);
                usuarioDAO.save(Session.usuarioSleccionado);

                dispose();
                var principal = new Principal();
                principal.setVisible(true);
            }else {
                JOptionPane.showMessageDialog(this,"La contraseña no es la correcta");
            }
        }else {
            JOptionPane.showMessageDialog(this,"El nombre de usuario no existe");
        }
    }
}
