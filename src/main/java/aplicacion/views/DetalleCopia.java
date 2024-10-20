package aplicacion.views;

import aplicacion.Session;
import aplicacion.JdbcUtils;
import aplicacion.dao.CopiaDAO;

import javax.swing.*;

public class DetalleCopia extends JDialog {
    private JPanel detailPane;
    private JButton botonVolver;
    private JTextField titleDetail;
    private JTextField genreDetail;
    private JTextField yearDetail;
    private JTextField directorDetail;
    private JTextArea descriptionDetail;
    private JTextField formatDetail;
    private JTextField conditionDetail;
    private JButton botonEliminar;

    CopiaDAO copiaDAO = new CopiaDAO(JdbcUtils.getConnection());

    public DetalleCopia(){
        setContentPane(detailPane);
        setModal(true);
        setTitle(Session.peliculaDTO.getTitulo());
        setLocationRelativeTo(null);
        setResizable(false);


        titleDetail.setText(Session.peliculaDTO.getTitulo());
        genreDetail.setText(Session.peliculaDTO.getGenero());
        yearDetail.setText(Session.peliculaDTO.getAño().toString());
        directorDetail.setText(Session.peliculaDTO.getDirector());
        descriptionDetail.setText(Session.peliculaDTO.getDescripcion());
        formatDetail.setText(Session.copiaSeleccionada.getSoporte());
        conditionDetail.setText(Session.copiaSeleccionada.getEstado());
        pack();

        botonVolver.addActionListener(e ->{
            ventanaAnterior();
        });

        botonEliminar.addActionListener(e -> {
            borrar();
        });
    }

    private void ventanaAnterior() {
        Session.peliculaDTO = null;
        var principal = new Principal();
        principal.setVisible(true);
        dispose();
    }

    private void borrar(){
        var resultado = JOptionPane.showConfirmDialog(this,"¿Eliminar la copia?");
        if (resultado == JOptionPane.YES_OPTION){
            Session.listadoCopiasDTO.remove(Session.copiaSeleccionada);
            copiaDAO.delete(Session.copiaSeleccionada);
            Session.peliculaDTO = null;
        }
        dispose();
        var principal = new Principal();
        principal.setVisible(true);
    }

}
