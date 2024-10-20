package aplicacion.models;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Usuario implements Serializable {
    private Integer id;
    private String nombre_usuario;
    private String contraseña;

    private List<Copia> micopia = new ArrayList<>(0);

    public Usuario() {

    }

    public Usuario(Integer id, String nombre_usuario, String contraseña) {
        this.id = id;
        this.nombre_usuario = nombre_usuario;
        this.contraseña = contraseña;
    }
}
