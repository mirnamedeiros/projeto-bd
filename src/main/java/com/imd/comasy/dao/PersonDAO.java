package com.imd.comasy.dao;

import com.imd.comasy.dao.connector.ConFactory;
import com.imd.comasy.model.Person;
import com.imd.comasy.utils.EnumRole;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

@Repository
public class PersonDAO {

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

    public List<Person> findAll() {
        List<Person> persons = new ArrayList<>();
        try {
            conectar();
            String sql = "SELECT * FROM person";
            ResultSet rs = comando.executeQuery(sql);
            while (rs.next()) {
                Person person = buildPerson(rs);
                loadPhoneNumbers(person);
                persons.add(person);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
        return persons;
    }

    public Person findById(String cpf) {
        Person person = null;
        try {
            conectar();
            String sql = "SELECT * FROM person WHERE cpf = " + retornaValorStringBD(cpf);
            ResultSet rs = comando.executeQuery(sql);
            if (rs.next()) {
                person = buildPerson(rs);
                loadPhoneNumbers(person);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
        return person;
    }

    public Person findByUsername(String username) {
        Person person = null;
        try {
            conectar();
            String sql = "SELECT * FROM person WHERE username = " + retornaValorStringBD(username);
            ResultSet rs = comando.executeQuery(sql);
            if (rs.next()) {
                person = buildPerson(rs);
                loadPhoneNumbers(person);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
        return person;
    }

    public int save(Person person) {
        int result = 0;
        try {
            conectar();
            StringBuffer buffer = new StringBuffer();
            buffer.append("INSERT INTO person (");
            buffer.append(retornarCamposBD());
            buffer.append(") VALUES (");
            buffer.append(retornarValoresBD(person));
            buffer.append(")");
            String sql = buffer.toString();

            result = comando.executeUpdate(sql);
            savePhoneNumbers(person);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
        return result;
    }

    public int update(Person person) {
        int result = 0;
        try {
            conectar();
            StringBuffer buffer = new StringBuffer();
            buffer.append("UPDATE person SET ");
            buffer.append(returnFieldValuesBD(person));
            buffer.append(" WHERE cpf = ");
            buffer.append(retornaValorStringBD(person.getCpf()));
            String sql = buffer.toString();

            result = comando.executeUpdate(sql);
            updatePhoneNumbers(person);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
        return result;
    }

    public int delete(String cpf) {
        int result = 0;
        try {
            conectar();
            String sql = "DELETE FROM person WHERE cpf = " + retornaValorStringBD(cpf);
            result = comando.executeUpdate(sql);
            deletePhoneNumbers(cpf);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
        return result;
    }

    private void savePhoneNumbers(Person person) {
        try {
            conectar();
            String sql = "INSERT INTO phone_numbers (person_cpf, phone_number) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            for (String phoneNumber : person.getPhoneNumber()) {
                statement.setString(1, person.getCpf());
                statement.setString(2, phoneNumber);

                statement.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
    }

    private void updatePhoneNumbers(Person person) {
        try {
            conectar();
            deletePhoneNumbers(person.getCpf());
            savePhoneNumbers(person);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
    }

    private void deletePhoneNumbers(String cpf) {
        try {
            conectar();
            String sql = "DELETE FROM phone_numbers WHERE person_cpf = " + retornaValorStringBD(cpf);
            comando.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
    }

    private void loadPhoneNumbers(Person person) {
        try {
            conectar();
            String sql = "SELECT phone_number FROM phone_numbers WHERE person_cpf = " + retornaValorStringBD(person.getCpf());
            ResultSet rs = comando.executeQuery(sql);
            List<String> phoneNumbers = new ArrayList<>();
            while (rs.next()) {
                phoneNumbers.add(rs.getString("phone_number"));
            }
            person.setPhoneNumber(phoneNumbers);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            fechar();
        }
    }

    private Person buildPerson(ResultSet rs) throws SQLException {
        Person person = new Person();
        person.setCpf(rs.getString("cpf"));
        person.setName(rs.getString("name"));
        person.setBirthday(rs.getDate("birthday"));
        person.setCnh(rs.getString("cnh"));
        person.setPhotoUrl(rs.getString("photo_url"));
        person.setUsername(rs.getString("username"));
        person.setPassword(rs.getString("password"));
        person.setRole(EnumRole.valueOf(rs.getString("role")));
        person.setPhoneNumber(new ArrayList<>());
        return person;
    }

    protected String returnFieldValuesBD(Person person) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("name = ");
        buffer.append(retornaValorStringBD(person.getName()));
        buffer.append(", birthday = ");
        buffer.append(retornaValorStringBD(person.getBirthday().toString()));
        buffer.append(", cnh = ");
        buffer.append(retornaValorStringBD(person.getCnh()));
        buffer.append(", photo_url = ");
        buffer.append(retornaValorStringBD(person.getPhotoUrl()));
        buffer.append(", username = ");
        buffer.append(retornaValorStringBD(person.getUsername()));
        buffer.append(", password = ");
        buffer.append(retornaValorStringBD(person.getPassword()));
        buffer.append(", role = ");
        buffer.append(retornaValorStringBD(person.getRole().name()));
        return buffer.toString();
    }

    private String retornarValoresBD(Person person) {
        return retornaValorStringBD(person.getCpf())
                + ", "
                + retornaValorStringBD(person.getName())
                + ", "
                + retornaValorStringBD(person.getBirthday().toString())
                + ", "
                + retornaValorStringBD(person.getCnh())
                + ", "
                + retornaValorStringBD(person.getPhotoUrl())
                + ", "
                + retornaValorStringBD(person.getUsername())
                + ", "
                + retornaValorStringBD(person.getPassword())
                + ", "
                + retornaValorStringBD(person.getRole().name());
    }

    private String retornaValorStringBD(String valor) {
        if (valor == null) {
            return "null";
        } else {
            return "'" + valor + "'";
        }
    }

    private String retornarCamposBD() {
        return "cpf, name, birthday, cnh, photo_url, username, password, role";
    }
}
