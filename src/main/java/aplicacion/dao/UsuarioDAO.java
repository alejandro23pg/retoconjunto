package aplicacion.dao;

import aplicacion.models.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements DAO<Usuario> {
    public static final String SELECT_FROM_USUARIO = "select * from usuario";
    public static final String SELECT_FROM_USUARIO_WHERE_ID = "select * from usuario where id = ?";
    public static final String INSERT_INTO_USUARIO = "insert into usuario (nombre_usuario, contraseña) values (?, ?)";
    public static final String UPDATE_USUARIO = "update usuario set nombre_usuario = ?, contraseña = ? where id = ?";
    public static final String DELETE_FROM_USUARIO = "delete from usuario where id = ?";
    public static final String SELECT_USUARIO_PASSWORD_FROM_USUARIO = "select * from usuario where nombre_usuario = ? and contraseña = ?";
    public static final String SELECT_NOMBRE_USUARIO_FROM_USUARIO = "select nombre_usuario from usuario where nombre_usuario = ?";

    private static Connection connection = null;

    public UsuarioDAO(Connection c) {
        connection = c;
    }
    @Override
    public List<Usuario> findAll() {
        var lista = new ArrayList<Usuario>();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(SELECT_FROM_USUARIO);

            while (rs.next()) {
                Usuario user = new Usuario();
                user.setId(rs.getInt("id"));
                user.setNombre_usuario(rs.getString("nombre_usuario"));
                user.setContraseña(rs.getString("contraseña"));
                lista.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    @Override
    public Usuario findById(Integer id) {
        Usuario usuario = null;

        try (PreparedStatement ps = connection.prepareStatement(SELECT_FROM_USUARIO_WHERE_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre_usuario(rs.getString("nombre_usuario"));
                usuario.setContraseña(rs.getString("contraseña"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }

    @Override
    public void save(Usuario usuario) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_INTO_USUARIO, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getNombre_usuario());
            ps.setString(2, usuario.getContraseña());

            if (ps.executeUpdate() == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                usuario.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Usuario usuario) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_USUARIO)) {
            ps.setString(1, usuario.getNombre_usuario());
            ps.setString(2, usuario.getContraseña());
            ps.setInt(3, usuario.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Usuario usuario) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_FROM_USUARIO)) {
            ps.setInt(1, usuario.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Usuario validateUser(String usuario, char[] contraseña) {
        Usuario usuarioValidado = null;
        try (PreparedStatement ps = connection.prepareStatement(SELECT_USUARIO_PASSWORD_FROM_USUARIO)) {
            String contraseñaValida = new String(contraseña);

            ps.setString(1, usuario);
            ps.setString(2, contraseñaValida);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuarioValidado = new Usuario();
                usuarioValidado.setId(rs.getInt("id"));
                usuarioValidado.setNombre_usuario(rs.getString("nombre_usuario"));
                usuarioValidado.setContraseña(rs.getString("contraseña"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuarioValidado;
    }

    public Usuario validateNewUser(String usuario){
        Usuario usuarioNuevo = null;
        try(PreparedStatement ps = connection.prepareStatement(SELECT_NOMBRE_USUARIO_FROM_USUARIO)) {
            ps.setString(1,usuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                usuarioNuevo = new Usuario();
                usuarioNuevo.setNombre_usuario(rs.getString("nombre_usuario"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuarioNuevo;
    }
}
