package com.imd.comasy.dao;

import com.imd.comasy.dao.connector.ConFactory;
import com.imd.comasy.dto.VisitorDTO;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VisitorDAO {

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

    public int save(VisitorDTO visitor) {
        int result = 0;
        try {
            conectar();
            StringBuffer buffer = new StringBuffer();
            buffer.append("INSERT INTO visitor (");
            buffer.append(retornarCamposBD());
            buffer.append(") VALUES (");
            buffer.append(retornarValoresBD(visitor));
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

    public List<VisitorDTO> findAll() {
        List<VisitorDTO> visitors = new ArrayList<>();
        try {
            conectar();
            String sql = "SELECT * FROM visitor";
            statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                visitors.add(buildVisitor(rs));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
        return visitors;
    }

    public VisitorDTO findByCode(Long code) {
        VisitorDTO visitor = null;
        try {
            conectar();
            String sql = "SELECT * FROM visitor WHERE code = ?";
            statement = connection.prepareStatement(sql);
            statement.setLong(1, code);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                visitor = buildVisitor(rs);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
        return visitor;
    }

    public int update(Long code, VisitorDTO visitor) {
        int result = 0;
        try {
            conectar();
            StringBuffer buffer = new StringBuffer();
            buffer.append("UPDATE visitor SET ");
            buffer.append(returnFieldValuesBD(visitor));
            buffer.append(" WHERE code = ");
            buffer.append(code);
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

    public int delete(Long code) {
        int result = 0;
        try {
            conectar();
            String sql = "DELETE FROM visitor WHERE code = ?";
            statement = connection.prepareStatement(sql);
            statement.setLong(1, code);
            result = statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
        return result;
    }

    private VisitorDTO buildVisitor(ResultSet rs) throws SQLException {
        VisitorDTO visitor = new VisitorDTO();
        visitor.setName(rs.getString("name"));
        visitor.setType(rs.getString("type"));
        return visitor;
    }

    private String retornaValorStringBD(String valor) {
        if (valor == null) {
            return "null";
        } else {
            return "'" + valor + "'";
        }
    }

    private String retornarCamposBD() {
        return "name, type";
    }

    private String retornarValoresBD(VisitorDTO visitor) {
        return retornaValorStringBD(visitor.getName())
                + ", "
                + retornaValorStringBD(visitor.getType());
    }

    private String returnFieldValuesBD(VisitorDTO visitor) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("name = ");
        buffer.append(retornaValorStringBD(visitor.getName()));
        buffer.append(", type = ");
        buffer.append(retornaValorStringBD(visitor.getType()));
        return buffer.toString();
    }
}
