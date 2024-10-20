package aplicacion.views;

import aplicacion.Session;
import aplicacion.JdbcUtils;
import aplicacion.dao.CopiaDAO;
import aplicacion.dao.UsuarioDAO;
import aplicacion.models.Copia;
import aplicacion.models.Usuario;

import javax.swing.*;
import java.util.List;

public class PantallaLogin extends JFrame {
    private JPanel ventana;
    private JPasswordField pass;
    private JTextField user;
    private JButton botonIniciarSesion;
    private JButton botonCerrarAplicacion;
    private JButton añadirUsuario;


    public PantallaLogin() {
        setContentPane(ventana);
        setTitle("Gestor de películas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setLocationRelativeTo(null);
        setResizable(false);

        botonIniciarSesion.addActionListener((e)->{
            sesionConectada();
        });

        botonCerrarAplicacion.addActionListener((e) -> {
                Session.paramsnotnull();
                dispose();
        });

        añadirUsuario.addActionListener((e)->{
            Session.paramsnotnull();
            AñadirUsuario nuevoUsuario = new AñadirUsuario();
            nuevoUsuario.setVisible(true);
            dispose();
        });
    }

    private void sesionConectada() {
        UsuarioDAO usuarioDAO = new UsuarioDAO(JdbcUtils.getConnection());
        CopiaDAO copiaDAO = new CopiaDAO(JdbcUtils.getConnection());

        Usuario u = usuarioDAO.validateUser(user.getText(), pass.getPassword());

        if (u != null){
            List<Copia> miCopia = copiaDAO.findByUser(u);
            u.setMicopia(miCopia);

            Session.listadoCopiasDTO = miCopia;
            Session.usuarioSleccionado = u;

            Principal miLista = new Principal();
            miLista.setVisible(true);
            dispose();

        }else{
            JOptionPane.showMessageDialog(this,"Error");
            user.setText("");
            pass.setText("");
        }
    }
}
