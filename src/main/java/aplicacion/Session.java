package aplicacion;

import aplicacion.models.Copia;
import aplicacion.models.Pelicula;
import aplicacion.models.Usuario;

import java.util.List;

public class Session {
    public static Usuario usuarioSleccionado = null;
    public static Copia copiaSeleccionada = null;
    public static List<Copia> listadoCopiasDTO = null;
    public static Pelicula peliculaDTO = null;

    public static void paramsnotnull(){
        usuarioSleccionado = null;
        copiaSeleccionada = null;
        listadoCopiasDTO = null;
        peliculaDTO = null;
    }
}
