package com.imd.comasy.dao;

import com.imd.comasy.dao.connector.ConFactory;
import com.imd.comasy.dto.ResidentDTO;
import com.imd.comasy.model.Holder;
import com.imd.comasy.model.Resident;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ResidentDAO {

    private Connection connection;
    private Statement comando;

    private void conectar() throws ClassNotFoundException, SQLException {
        connection = new ConFactory().conexao();
        comando = connection.createStatement();
        System.out.println("Conectado!");
    }

    private void fechar() {
        try {
            if (comando != null) {
                comando.close();
            }
            if (connection != null) {
                connection.close();
            }
            System.out.println("Conexão Fechada");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Resident> findAll() {
        List<Resident> residents = new ArrayList<>();
        try {
            conectar();
            String sql = "SELECT * FROM resident";
            ResultSet rs = comando.executeQuery(sql);
            while (rs.next()) {
                Resident resident = buildResident(rs);
                residents.add(resident);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
        return residents;
    }

    public Resident findById(String personCpf) {
        Resident resident = null;
        try {
            conectar();
            String sql = "SELECT * FROM resident WHERE person_cpf = " + retornaValorStringBD(personCpf);
            ResultSet rs = comando.executeQuery(sql);
            if (rs.next()) {
                resident = buildResident(rs);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
        return resident;
    }

    public int save(ResidentDTO resident) {
        int result = 0;
        try {
            conectar();

            // Inserir o holder se não existir
            Holder holder = findHolderByCpf(resident.getHolderCpf());
            if (holder == null) {
                insertHolder(new Holder(resident.getHolderCpf(), resident.getApartmentNumber(), resident.getBlockNumber()));
            }

            StringBuffer buffer = new StringBuffer();
            buffer.append("INSERT INTO resident (person_cpf, holder_cpf) VALUES (");
            buffer.append(retornaValorStringBD(resident.getPersonCpf()));
            buffer.append(", ");
            buffer.append(retornaValorStringBD(resident.getHolderCpf()));
            buffer.append(")");
            String sql = buffer.toString();
            result = comando.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
        return result;
    }

    public int update(ResidentDTO resident) {
        int result = 0;
        try {
            conectar();

            Holder newHolder = findHolderByCpf(resident.getHolderCpf());
            if (newHolder == null) {
                insertHolder(new Holder(resident.getHolderCpf(), resident.getApartmentNumber(), resident.getBlockNumber()));
            }

            StringBuffer buffer = new StringBuffer();
            buffer.append("UPDATE resident SET holder_cpf = ");
            buffer.append(retornaValorStringBD(resident.getHolderCpf()));
            buffer.append(" WHERE person_cpf = ");
            buffer.append(retornaValorStringBD(resident.getPersonCpf()));
            String sql = buffer.toString();
            result = comando.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
        return result;
    }

    public int delete(String personCpf) {
        int result = 0;
        try {
            conectar();
            String sql = "DELETE FROM resident WHERE person_cpf = " + retornaValorStringBD(personCpf);
            result = comando.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
        return result;
    }

    private Resident buildResident(ResultSet rs) throws SQLException {
        Resident resident = new Resident();
        resident.setPersonCpf(rs.getString("person_cpf"));
        resident.setHolderCpf(rs.getString("holder_cpf"));
        return resident;
    }

    private String retornaValorStringBD(String valor) {
        if (valor == null) {
            return "null";
        } else {
            return "'" + valor + "'";
        }
    }

    private Holder findHolderByCpf(String cpf) {
        Holder holder = null;
        try {
            String sql = "SELECT * FROM holder WHERE holder_cpf = " + retornaValorStringBD(cpf);
            ResultSet rs = comando.executeQuery(sql);
            if (rs.next()) {
                holder = buildHolder(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return holder;
    }

    private int insertHolder(Holder holder) {
        int result = 0;
        try {
            StringBuffer buffer = new StringBuffer();
            buffer.append("INSERT INTO holder (holder_cpf, apartment_number, block_number) VALUES (");
            buffer.append(retornaValorStringBD(holder.getHolderCpf()));
            buffer.append(", ");
            buffer.append(retornaValorStringBD(String.valueOf(holder.getApartmentNumber())));
            buffer.append(", ");
            buffer.append(retornaValorStringBD(String.valueOf(holder.getBlockNumber())));
            buffer.append(")");
            String sql = buffer.toString();
            result = comando.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Holder buildHolder(ResultSet rs) throws SQLException {
        Holder holder = new Holder();
        holder.setHolderCpf(rs.getString("holder_cpf"));
        holder.setApartmentNumber(rs.getInt("apartment_number"));
        holder.setBlockNumber(rs.getInt("block_number"));
        return holder;
    }
}

