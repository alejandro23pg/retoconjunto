package aplicacion.views;

import aplicacion.Session;
import aplicacion.models.Pelicula;
import aplicacion.JdbcUtils;
import aplicacion.dao.CopiaDAO;
import aplicacion.dao.PeliculaDAO;
import aplicacion.models.Copia;

import javax.swing.*;
import java.util.List;

public class AñadirCopia extends JDialog {
    private JPanel ventanaAdd;
    private JComboBox<String> soporteCombo;
    private JComboBox<String> peliculaCombo;
    private JRadioButton buenoRadioButton;
    private JRadioButton dañadoRadioButton;
    private JButton botonGuardar;
    private JButton botonCancelar;
    private JLabel warning;

    private CopiaDAO copiaDAO = new CopiaDAO(JdbcUtils.getConnection());
    private PeliculaDAO peliculaDAO = new PeliculaDAO(JdbcUtils.getConnection());
    PeliculaDAO peliculaDAO1 = new PeliculaDAO(JdbcUtils.getConnection());

    public AñadirCopia(){
        setContentPane(ventanaAdd);
        setModal(true);
        setTitle("Añadir Copia");
        setLocationRelativeTo(null);
        setSize(300,250);
        setResizable(false);

        setearOpcionesSoporte();

        setearOpcionesPeliculas(peliculaDAO1);

        botonGuardar.addActionListener(e ->{
            guardarCopia();
        });

        botonCancelar.addActionListener(e ->{
            cancelar();
        });

    }

    private void setearOpcionesPeliculas(PeliculaDAO peliculaDAO) {
        var opcionesPeliculas = new DefaultComboBoxModel<String>();
        peliculaCombo.setModel(opcionesPeliculas);
        List<Pelicula> listaPeliculas = peliculaDAO.findAll();
        for (Pelicula pelicula : listaPeliculas){
            opcionesPeliculas.addElement(pelicula.getTitulo());
        }
    }

    private void setearOpcionesSoporte() {
        var opcionesSoporte = new DefaultComboBoxModel<String>();
        soporteCombo.setModel(opcionesSoporte);
        opcionesSoporte.addElement("VHS");
        opcionesSoporte.addElement("DVD");
        opcionesSoporte.addElement("Blu-ray");
        opcionesSoporte.addElement("Digital");
    }

    private void guardarCopia() {
        Session.copiaSeleccionada = new Copia();
        String titulo = (String) peliculaCombo.getSelectedItem();
        Session.peliculaDTO = peliculaDAO.findByTitle(titulo);

        if ( Session.peliculaDTO != null && soportePelicula() && estadoPelicula()) {
            soportePelicula();
            estadoPelicula();

            Copia nuevaCopia = new Copia();
            nuevaCopia.setEstado(Session.copiaSeleccionada.getEstado());
            nuevaCopia.setSoporte(Session.copiaSeleccionada.getSoporte());
            nuevaCopia.setId_pelicula(Session.peliculaDTO.getId());
            nuevaCopia.setId_usuario(Session.usuarioSleccionado.getId());
            nuevaCopia.setPelicula(Session.peliculaDTO);
            nuevaCopia.setUsuario(Session.usuarioSleccionado);

            Session.listadoCopiasDTO.add(nuevaCopia);
            copiaDAO.save(nuevaCopia);

            dispose();
            var principal = new Principal();
            principal.setVisible(true);

        } else{
            warning.setText("Faltan valores por introducir");
        }
    }
    private boolean soportePelicula(){
        boolean flag = false;
        if (soporteCombo.getSelectedItem() != null){
            Session.copiaSeleccionada.setSoporte((String)soporteCombo.getSelectedItem());
            flag = true;
        }
        return flag;
    }

    private boolean estadoPelicula() {
        boolean flag = false;
        if (buenoRadioButton.isSelected()){
            Session.copiaSeleccionada.setEstado("bueno");
            flag = true;
        } else if (dañadoRadioButton.isSelected()) {
            Session.copiaSeleccionada.setEstado("dañado");
            flag = true;
        }
        return flag;
    }

    private void cancelar() {
        dispose();
        var principal = new Principal();
        principal.setVisible(true);
    }
}
