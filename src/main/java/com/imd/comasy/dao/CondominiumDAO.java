package com.imd.comasy.dao;

import com.imd.comasy.dao.connector.ConFactory;
import com.imd.comasy.model.Condominium;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CondominiumDAO {

    private Connection connection;
    private PreparedStatement statement;

    private void conectar() throws ClassNotFoundException, SQLException {
        connection = new ConFactory().conexao();
    }

    private void fechar() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int save(Condominium condominium) {
        int result = 0;
        try {
            conectar();
            StringBuffer buffer = new StringBuffer();
            buffer.append("INSERT INTO condominium (");
            buffer.append(retornarCamposBD());
            buffer.append(") VALUES (");
            buffer.append(retornarValoresBD(condominium));
            buffer.append(")");
            String sql = buffer.toString();

            statement = connection.prepareStatement(sql);
            result = statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
        return result;
    }

    public Condominium findById(int id) {
        Condominium condominium = null;
        try {
            conectar();
            String sql = "SELECT * FROM condominium WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                condominium = buildCondominium(rs);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
        return condominium;
    }

    public List<Condominium> findAll() {
        List<Condominium> condominiums = new ArrayList<>();
        try {
            conectar();
            String sql = "SELECT * FROM condominium";
            statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                condominiums.add(buildCondominium(rs));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
        return condominiums;
    }

    public int update(int id, Condominium condominium) {
        int result = 0;
        try {
            conectar();
            StringBuffer buffer = new StringBuffer();
            buffer.append("UPDATE condominium SET ");
            buffer.append(returnFieldValuesBD(condominium));
            buffer.append(" WHERE id = ?");
            String sql = buffer.toString();

            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            result = statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
        return result;
    }

    public int delete(int id) {
        int result = 0;
        try {
            conectar();
            String sql = "DELETE FROM condominium WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            result = statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
        return result;
    }

    private Condominium buildCondominium(ResultSet rs) throws SQLException {
        Condominium condominium = new Condominium();
        condominium.setId(rs.getInt("id"));
        condominium.setName(rs.getString("name"));
        condominium.setCnpj(rs.getString("cnpj"));
        condominium.setCep(rs.getString("cep"));
        condominium.setNeighborhood(rs.getString("neighborhood"));
        condominium.setStreet(rs.getString("street"));
        condominium.setCity(rs.getString("city"));
        condominium.setState(rs.getString("state"));
        condominium.setPhone(rs.getString("phonenumber"));
        return condominium;
    }

    private String retornaValorStringBD(String valor) {
        if (valor == null) {
            return "null";
        } else {
            return "'" + valor + "'";
        }
    }

    private String retornarCamposBD() {
        return "name, cnpj, cep, neighborhood, street, city, state, phonenumber";
    }

    private String retornarValoresBD(Condominium condominium) {
        return "'" + condominium.getName() + "'," +
                "'" + condominium.getCnpj() + "', " +
                "'" + condominium.getCep() + "', " +
                "'" + condominium.getNeighborhood() + "', " +
                "'" + condominium.getStreet() + "', " +
                "'" + condominium.getCity() + "', " +
                "'" + condominium.getState() + "', " +
                "'" + condominium.getPhone() + "'";
    }

    private String returnFieldValuesBD(Condominium condominium) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("name = ");
        buffer.append(retornaValorStringBD(condominium.getName()));
        buffer.append(", cnpj = ");
        buffer.append(retornaValorStringBD(condominium.getCnpj()));
        buffer.append(", cep = ");
        buffer.append(retornaValorStringBD(condominium.getCep()));
        buffer.append(", neighborhood = ");
        buffer.append(retornaValorStringBD(condominium.getNeighborhood()));
        buffer.append(", street = ");
        buffer.append(retornaValorStringBD(condominium.getStreet()));
        buffer.append(", city = ");
        buffer.append(retornaValorStringBD(condominium.getCity()));
        buffer.append(", state = ");
        buffer.append(retornaValorStringBD(condominium.getState()));
        buffer.append(", phonenumber = ");
        buffer.append(retornaValorStringBD(condominium.getPhone()));
        return buffer.toString();
    }

}
