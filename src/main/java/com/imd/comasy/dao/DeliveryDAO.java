package com.imd.comasy.dao;

import com.imd.comasy.dao.connector.ConFactory;
import com.imd.comasy.model.Delivery;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DeliveryDAO {

    private Connection connection;
    private Statement statement;

    private void connect() throws ClassNotFoundException, SQLException {
        connection = new ConFactory().conexao();
        statement = connection.createStatement();
        System.out.println("Connected!");
    }

    private void close() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
            System.out.println("Connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Delivery> findAll() {
        List<Delivery> deliveries = new ArrayList<>();
        try {
            connect();
            String sql = "SELECT * FROM delivery";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Delivery delivery = buildDelivery(resultSet);
                deliveries.add(delivery);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return deliveries;
    }

    public Delivery findById(Integer id) {
        Delivery delivery = null;
        try {
            connect();
            String sql = "SELECT * FROM delivery WHERE id = " + id;
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                delivery = buildDelivery(resultSet);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return delivery;
    }

    public int save(Delivery delivery) {
        int result = 0;
        try {
            connect();
            StringBuffer buffer = new StringBuffer();
            buffer.append("INSERT INTO delivery (");
            buffer.append(retornarCamposBD());
            buffer.append(") VALUES (");
            buffer.append(retornarValoresBD(delivery));
            buffer.append(")");
            String sql = buffer.toString();
            result = statement.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }

    public int update(Delivery delivery) {
        int result = 0;
        try {
            connect();
            StringBuffer buffer = new StringBuffer();
            buffer.append("UPDATE delivery SET ");
            buffer.append(returnFieldValuesBD(delivery));
            buffer.append(" WHERE id = ");
            buffer.append(delivery.getId());
            String sql = buffer.toString();
            result = statement.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }

    public int delete(Integer id) {
        int result = 0;
        try {
            connect();
            StringBuffer buffer = new StringBuffer();
            buffer.append("DELETE FROM delivery WHERE id = ");
            buffer.append(id);
            String sql = buffer.toString();
            result = statement.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }

    protected String returnFieldValuesBD(Delivery delivery) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("status = ");
        buffer.append(retornaValorStringBD(delivery.getStatus()));
        buffer.append(", resident_cpf = ");
        buffer.append(retornaValorStringBD(String.valueOf(delivery.getResidentCpf())));
        buffer.append(", arrivalDate = ");
        buffer.append(retornaValorStringBD(delivery.getArrivalDate().toString()));
        buffer.append(", doorman_cpf = ");
        buffer.append(retornaValorStringBD(delivery.getDoormanCpf()));
        return buffer.toString();
    }

    private String retornarValoresBD(Delivery delivery) {
        return retornaValorStringBD(delivery.getStatus())
                + ", "
                + retornaValorStringBD(String.valueOf(delivery.getResidentCpf()))
                + ", "
                + retornaValorStringBD(delivery.getArrivalDate().toString())
                + ", "
                + retornaValorStringBD(delivery.getDoormanCpf());
    }

    private String retornaValorStringBD(String valor) {
        if (valor == null) {
            return "null";
        } else {
            return "'" + valor + "'";
        }
    }

    private String retornarCamposBD() {
        return "status, resident_cpf, arrivalDate, doorman_cpf";
    }

    private Delivery buildDelivery(ResultSet resultSet) throws SQLException {
        Delivery delivery = new Delivery();
        delivery.setId(resultSet.getInt("id"));
        delivery.setStatus(resultSet.getString("status"));
        delivery.setResidentCpf(resultSet.getString("resident_cpf"));
        delivery.setArrivalDate(resultSet.getDate("arrivalDate"));
        delivery.setDoormanCpf(resultSet.getString("doorman_cpf"));
        return delivery;
    }
}

