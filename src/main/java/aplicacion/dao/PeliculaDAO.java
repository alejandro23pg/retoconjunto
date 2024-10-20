package aplicacion.dao;

import aplicacion.models.Pelicula;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeliculaDAO implements DAO<Pelicula>{
    public static final String SELECT_FROM_PELICULA = "select * from pelicula";
    public static final String SELECT_FROM_PELICULA_WHERE_ID = "select * from pelicula where id = ?";
    public static final String INSERT_INTO_PELICULA = "insert into pelicula (titulo, genero, año, descripcion, director) values (?, ?, ?, ?, ?)";
    public static final String UPDATE_PELICULA = "update pelicula set titulo = ?, genero = ?, año = ?, descripcion = ?, director = ? where id = ?";
    public static final String DELETE_FROM = "delete from pelicula where id = ?";
    public static final String SELECT_FROM_PELICULA_WHERE_TITULO = "select * from pelicula where titulo = ?";

    private static Connection connection = null;

    public PeliculaDAO(Connection c){
        connection = c;
    }

    @Override
    public List<Pelicula> findAll() {
        var lista = new ArrayList<Pelicula>();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(SELECT_FROM_PELICULA);

            while(rs.next()){
                Pelicula peli = new Pelicula();
                peli.setId(rs.getInt("id"));
                peli.setTitulo(rs.getString("titulo"));
                peli.setGenero(rs.getString("genero"));
                peli.setAño(rs.getInt("año"));
                peli.setDescripcion(rs.getString("descripcion"));
                peli.setDirector(rs.getString("director"));
                lista.add(peli);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    @Override
    public Pelicula findById(Integer id) {
        Pelicula pelicula = null;

        try(PreparedStatement ps = connection.prepareStatement(SELECT_FROM_PELICULA_WHERE_ID)) {
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                pelicula = new Pelicula();
                pelicula.setId(rs.getInt("id"));
                pelicula.setTitulo(rs.getString("titulo"));
                pelicula.setGenero(rs.getString("genero"));
                pelicula.setAño(rs.getInt("año"));
                pelicula.setDescripcion(rs.getString("descripcion"));
                pelicula.setDirector(rs.getString("director"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pelicula;
    }

    @Override
    public void save(Pelicula pelicula) {
        try(PreparedStatement ps = connection.prepareStatement(INSERT_INTO_PELICULA, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1,pelicula.getTitulo());
            ps.setString(2, pelicula.getGenero());
            ps.setInt(3,pelicula.getAño());
            ps.setString(4,pelicula.getDescripcion());
            ps.setString(5,pelicula.getDirector());

            if(ps.executeUpdate() == 1){
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                pelicula.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Pelicula pelicula) {
        try(PreparedStatement ps = connection.prepareStatement(UPDATE_PELICULA)) {
            ps.setString(1,pelicula.getTitulo());
            ps.setString(2,pelicula.getGenero());
            ps.setInt(3,pelicula.getAño());
            ps.setString(4, pelicula.getDescripcion());
            ps.setString(5, pelicula.getDirector());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Pelicula pelicula) {
        try(PreparedStatement ps = connection.prepareStatement(DELETE_FROM)) {
            ps.setInt(1,pelicula.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Pelicula findByTitle(String titulo){
        Pelicula pelicula = null;
        try(PreparedStatement ps = connection.prepareStatement(SELECT_FROM_PELICULA_WHERE_TITULO)) {
            ps.setString(1,titulo);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                pelicula = new Pelicula(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getString(6)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pelicula;
    }
}
