package aplicacion.dao;

import aplicacion.models.Copia;
import aplicacion.models.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CopiaDAO implements DAO<Copia>{
    public static final String SELECT_FROM_COPIA = "select * from copia";
    public static final String SELECT_FROM_COPIA_WHERE_ID = "select * from copia where id = ?";
    public static final String INSERT_INTO_COPIA = "insert into copia (estado, soporte, id_pelicula, id_usuario) values (?, ?, ?, ?)";
    public static final String UPDATE_COPIA = "update copia set estado = ?, soporte = ? where id = ?";
    public static final String DELETE_FROM_COPIA = "delete from copia where id = ?";
    public static final String SELECT_FROM_COPIA_WHERE_ID_USUARIO = "select * from copia where id_usuario = ?";

    private static Connection connection = null;
    public CopiaDAO (Connection c){
        connection = c;
    }

    @Override
    public List<Copia> findAll() {
        var lista = new ArrayList<Copia>();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(SELECT_FROM_COPIA);

            while(rs.next()){
                Copia copy = new Copia();
                copy.setId(rs.getInt("id"));
                copy.setEstado(rs.getString("estado"));
                copy.setSoporte(rs.getString("soporte"));
                copy.setId_pelicula(rs.getInt("id_pelicula"));
                copy.setId_usuario(rs.getInt("id_usuario"));
                lista.add(copy);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    @Override
    public Copia findById(Integer id) {
        Copia copia = null;

        try(PreparedStatement ps = connection.prepareStatement(SELECT_FROM_COPIA_WHERE_ID)) {
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                copia = new Copia();
                copia.setId(rs.getInt("id"));
                copia.setEstado(rs.getString("estado"));
                copia.setSoporte(rs.getString("soporte"));
                copia.setId_pelicula(rs.getInt("id_pelicula"));
                copia.setId_usuario(rs.getInt("id_usuario"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return copia;
    }

    @Override
    public void save(Copia copia) {
        try(PreparedStatement ps = connection.prepareStatement(INSERT_INTO_COPIA, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1,copia.getEstado());
            ps.setString(2,copia.getSoporte());
            ps.setInt(3,copia.getId_pelicula());
            ps.setInt(4,copia.getId_usuario());

            if(ps.executeUpdate() == 1){
                ResultSet keys = ps.getGeneratedKeys();
                keys.next();
                Integer copia_id = keys.getInt(1);
                copia.setId(copia_id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Copia copia) {
        try(PreparedStatement ps = connection.prepareStatement(UPDATE_COPIA)) {
            ps.setString(1, copia.getEstado());
            ps.setString(2, copia.getSoporte());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Copia copia) {
        try(PreparedStatement ps = connection.prepareStatement(DELETE_FROM_COPIA)) {
            ps.setInt(1, copia.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Copia> findByUser(Usuario u) {
        Integer id = u.getId();
        var miLista = new ArrayList<Copia>(0);

        try(PreparedStatement ps = connection.prepareStatement(SELECT_FROM_COPIA_WHERE_ID_USUARIO)) {
            ps.setInt(1,id);
            var resultado = ps.executeQuery();

            while(resultado.next()){
                Copia copia = new Copia(
                        resultado.getInt(1),
                        resultado.getString(2),
                        resultado.getString(3),
                        resultado.getInt(4),
                        resultado.getInt(5)
                );
                copia.setId_usuario(id);
                miLista.add(copia);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return miLista;
    }
}
