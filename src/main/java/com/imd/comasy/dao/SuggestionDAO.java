package com.imd.comasy.dao;

import com.imd.comasy.dao.connector.ConFactory;
import com.imd.comasy.dto.SuggestionDTO;
import com.imd.comasy.model.Suggestion;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SuggestionDAO {

    private Connection connection;
    private Statement comando;

    private void conectar() throws ClassNotFoundException, SQLException {
        connection = new ConFactory().conexao();
        comando = connection.createStatement();
        System.out.println("Conectado!");
    }

    private void fechar(){
        try {
            if (comando != null){
                comando.close();
            }
            if (connection != null){
                connection.close();
            }
            System.out.println("Conexão Fechada");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Suggestion> findAll() {
        List<Suggestion> suggestions = new ArrayList<>();
        try {
            conectar();
            String sql = "SELECT * FROM suggestion";
            ResultSet rs = comando.executeQuery(sql);
            while (rs.next()){
                Suggestion suggestion = buildSuggestion(rs);
                suggestions.add(suggestion);
            }
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        } finally {
            fechar();
        }
        return suggestions;
    }

    public Suggestion findById(Long id){
        Suggestion suggestion = null;
        try {
            conectar();
            String sql = "SELECT * FROM suggestion WHERE suggestion_id = "+ id;
            ResultSet rs = comando.executeQuery(sql);
            if(rs.next()){
                suggestion = buildSuggestion(rs);
            }
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        } finally {
            fechar();
        }
        return suggestion;
    }

    public int save(SuggestionDTO suggestionDTO){
        int result = 0;
        try {
            conectar();
            StringBuffer buffer = new StringBuffer();
            buffer.append("INSERT INTO suggestion (type, message, qtd_votos, data_proposta, resident_id, active) VALUES (");
            buffer.append(retornaValorStringBD(suggestionDTO.getType()));
            buffer.append(", ");
            buffer.append(retornaValorStringBD(suggestionDTO.getMessage()));
            buffer.append(", ");
            buffer.append(suggestionDTO.getQtdVotos());
            buffer.append(", ");
            buffer.append(retornaValorStringBD(new java.sql.Date(suggestionDTO.getDataProposta().getTime()).toString()));
            buffer.append(", ");
            buffer.append(retornaValorStringBD(suggestionDTO.getResidentId()));
            buffer.append(", ");
            buffer.append(suggestionDTO.getActive());
            buffer.append(")");
            String sql = buffer.toString();
            result = comando.executeUpdate(sql);
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        } finally {
            fechar();
        }
        return result;
    }

    public int update(SuggestionDTO suggestionDTO) {
        int result = 0;
        try {
            conectar();
            StringBuffer buffer = new StringBuffer();
            buffer.append("UPDATE suggestion SET type = ");
            buffer.append(retornaValorStringBD(suggestionDTO.getType()));
            buffer.append(", message = ");
            buffer.append(retornaValorStringBD(suggestionDTO.getMessage()));
            buffer.append(", qtd_votos = ");
            buffer.append(suggestionDTO.getQtdVotos());
            buffer.append(", data_proposta = ");
            buffer.append(retornaValorStringBD(new java.sql.Date(suggestionDTO.getDataProposta().getTime()).toString()));
            buffer.append(", resident_id = ");
            buffer.append(retornaValorStringBD(suggestionDTO.getResidentId()));
            buffer.append(", active = ");
            buffer.append(suggestionDTO.getActive());
            buffer.append(" WHERE suggestion_id = ");
            buffer.append(suggestionDTO.getId());
            String sql = buffer.toString();
            result = comando.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
        return result;
    }

    public int delete(Long id){
        int result = 0;
        try{
            conectar();
            String sql = "DELETE FROM suggestion WHERE suggestion_id = "+ id;
            result = comando.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        } finally {
            fechar();
        }
        return result;
    }

    private Suggestion buildSuggestion(ResultSet rs) throws SQLException{
        Suggestion suggestion = new Suggestion();
        suggestion.setId(rs.getLong("suggestion_id"));
        suggestion.setId(rs.getLong("suggestion_id"));
        suggestion.setType(rs.getString("type"));
        suggestion.setMessage(rs.getString("message"));
        suggestion.setQtdVotos(rs.getInt("qtd_votos"));
        suggestion.setDataProposta(rs.getDate("data_proposta"));
        suggestion.setResidentId(rs.getString("resident_id"));
        suggestion.setActive(rs.getBoolean("active"));
        return suggestion;
    }

    private String retornaValorStringBD(String valor){
        if(valor == null){
            return "null";
        } else {
            return "'" + valor + "'";
        }
    }
}
