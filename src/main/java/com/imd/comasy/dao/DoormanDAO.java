package com.imd.comasy.dao;

import com.imd.comasy.dao.connector.ConFactory;
import com.imd.comasy.dto.DoormanDTO;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class DoormanDAO {

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

    // Implementação para salvar o doorman na tabela 'doorman'
    public void save(DoormanDTO data) {
        try {
            conectar();
            String sql = "INSERT INTO doorman (cpf) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, data.getCpf());
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
    }
}
