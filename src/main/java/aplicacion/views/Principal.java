package aplicacion.views;

import aplicacion.Session;
import aplicacion.JdbcUtils;
import aplicacion.dao.PeliculaDAO;
import aplicacion.models.Copia;
import aplicacion.models.Pelicula;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

public class Principal extends JFrame{
    private JPanel ventanaLista;
    private JTable listadoPeliculas;
    private DefaultTableModel model;
    private JButton botonVolver;
    private JButton botonSalir;
    private JButton botonAñadir;

    public Principal(){
        String [] campos ={"Título","Estado","Soporte"};
        model = new DefaultTableModel(campos,0);
        listadoPeliculas.setModel(model);
        var tituloPelicula = listadoPeliculas.getColumnModel().getColumn(0);
        tituloPelicula.setPreferredWidth(300);

        mostrarListaCopias();

        setContentPane(ventanaLista);
        setTitle("Listado de películas - "+ Session.usuarioSleccionado.getNombre_usuario());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setLocationRelativeTo(null);
        setResizable(false);

        listadoPeliculas.getSelectionModel().addListSelectionListener(this::detalleCopia);

        botonAñadir.addActionListener(e ->{
            ventanaAñadirCopia();
        });

        botonVolver.addActionListener((e)-> {
            volverLogin();
        });

        botonSalir.addActionListener((e) -> {
                Session.paramsnotnull();
                dispose();
        });
    }

    private void mostrarListaCopias() {
        for(Copia copia : Session.listadoCopiasDTO){
            PeliculaDAO peliculaDAO = new PeliculaDAO(JdbcUtils.getConnection());
            Pelicula pelicula = peliculaDAO.findById(copia.getId_pelicula());
            copia.setPelicula(pelicula);
            Object[] fila ={pelicula.getTitulo(),copia.getEstado(), copia.getSoporte()};
            model.addRow(fila);
        }
    }

    private void volverLogin() {
        Session.paramsnotnull();
        dispose();
        PantallaLogin ventanaAnterior = new PantallaLogin();
        ventanaAnterior.setVisible(true);
    }

    private void ventanaAñadirCopia() {
        Session.peliculaDTO = null;
        Session.copiaSeleccionada = null;
        var añadirCopia = new AñadirCopia();
        añadirCopia.setVisible(true);
        dispose();
    }

    private void detalleCopia(ListSelectionEvent e) {
        PeliculaDAO peliDao = new PeliculaDAO(JdbcUtils.getConnection());
        if (e.getValueIsAdjusting()) return;

        int seleccion = listadoPeliculas.getSelectedRow();
        Session.copiaSeleccionada = Session.listadoCopiasDTO.get(seleccion);
        var idPelicula = Session.listadoCopiasDTO.get(seleccion).getId_pelicula();

        Session.peliculaDTO = peliDao.findById(idPelicula);

        var detalle = new DetalleCopia();
        detalle.setVisible(true);
        dispose();
    }
}
