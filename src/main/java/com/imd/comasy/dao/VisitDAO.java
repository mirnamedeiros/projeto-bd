package com.imd.comasy.dao;

import com.imd.comasy.dao.connector.ConFactory;
import com.imd.comasy.model.Visit;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VisitDAO {

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

    public int save(Visit visit) {
        int result = 0;
        try {
            conectar();
            StringBuffer buffer = new StringBuffer();
            buffer.append("INSERT INTO visit (");
            buffer.append(retornarCamposBD());
            buffer.append(") VALUES (");
            buffer.append(retornarValoresBD(visit));
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

    public List<Visit> findAll() {
        List<Visit> visits = new ArrayList<>();
        try {
            conectar();
            String sql = "SELECT * FROM visit";
            statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                visits.add(buildVisit(rs));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
        return visits;
    }

    public Visit findByIds(String doormanCpf, int visitorCode, String residentCpf) {
        Visit visit = null;
        try {
            conectar();
            String sql = "SELECT * FROM visit WHERE doorman_cpf = ? AND visitor_code = ? AND resident_cpf = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, doormanCpf);
            statement.setInt(2, visitorCode);
            statement.setString(3, residentCpf);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                visit = buildVisit(rs);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
        return visit;
    }

    public int update(String doormanCpf, int visitorCode, String residentCpf, Visit visit) {
        int result = 0;
        try {
            conectar();
            StringBuffer buffer = new StringBuffer();
            buffer.append("UPDATE visit SET ");
            buffer.append(returnFieldValuesBD(visit));
            buffer.append(" WHERE doorman_cpf = ? AND visitor_code = ? AND resident_cpf = ?");
            String sql = buffer.toString();

            statement = connection.prepareStatement(sql);
            statement.setString(1, doormanCpf);
            statement.setInt(2, visitorCode);
            statement.setString(3, residentCpf);
            result = statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
        return result;
    }

    public int delete(String doormanCpf, int visitorCode, String residentCpf) {
        int result = 0;
        try {
            conectar();
            String sql = "DELETE FROM visit WHERE doorman_cpf = ? AND visitor_code = ? AND resident_cpf = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, doormanCpf);
            statement.setInt(2, visitorCode);
            statement.setString(3, residentCpf);
            result = statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
        return result;
    }

    private Visit buildVisit(ResultSet rs) throws SQLException {
        Visit visit = new Visit();
        visit.setDoormanCpf(rs.getString("doorman_cpf"));
        visit.setVisitorCode(rs.getInt("visitor_code"));
        visit.setResidentCpf(rs.getString("resident_cpf"));
        visit.setStatus(rs.getString("status"));
        visit.setArrivalDate(rs.getTimestamp("arrival_date"));
        visit.setDepartureDate(rs.getTimestamp("departure_date"));
        return visit;
    }

    private String retornaValorStringBD(String valor) {
        if (valor == null) {
            return "null";
        } else {
            return "'" + valor + "'";
        }
    }

    private String retornaValorTimestampBD(Timestamp valor) {
        if (valor == null) {
            return "null";
        } else {
            return "'" + valor.toString() + "'";
        }
    }

    private String retornarCamposBD() {
        return "doorman_cpf, visitor_code, resident_cpf, status, arrival_date, departure_date";
    }

    private String retornarValoresBD(Visit visit) {
        return retornaValorStringBD(visit.getDoormanCpf())
                + ", "
                + visit.getVisitorCode()
                + ", "
                + retornaValorStringBD(visit.getResidentCpf())
                + ", "
                + retornaValorStringBD(visit.getStatus())
                + ", "
                + retornaValorTimestampBD(visit.getArrivalDate())
                + ", "
                + retornaValorTimestampBD(visit.getDepartureDate());
    }

    private String returnFieldValuesBD(Visit visit) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("doorman_cpf = ");
        buffer.append(retornaValorStringBD(visit.getDoormanCpf()));
        buffer.append(", visitor_code = ");
        buffer.append(visit.getVisitorCode());
        buffer.append(", resident_cpf = ");
        buffer.append(retornaValorStringBD(visit.getResidentCpf()));
        buffer.append(", status = ");
        buffer.append(retornaValorStringBD(visit.getStatus()));
        buffer.append(", arrival_date = ");
        buffer.append(retornaValorTimestampBD(visit.getArrivalDate()));
        buffer.append(", departure_date = ");
        buffer.append(retornaValorTimestampBD(visit.getDepartureDate()));
        return buffer.toString();
    }
}

