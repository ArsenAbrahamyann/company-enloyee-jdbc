package manager;

import db.DBConnectionProvider;
import model.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyManager {
    private Connection connection = DBConnectionProvider.getInstance().getConnection();

    public void save(Company company) {
        String sql = "INSERT INTO company(name,country) VALUES (?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, company.getName());
            preparedStatement.setString(2, company.getCountry());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                company.setId(generatedKeys.getInt(1));
            }
            System.out.println("Company inserted in to DB");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Company getById(Integer id) {

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM company");
            if (resultSet.next()) {
                return getCompanyFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Company> getAll() {
        List<Company> companyList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM company");
            while (resultSet.next()) {
                companyList.add(getCompanyFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companyList;
    }

    public void removeById(int companyId) {
        String sql = "DELETE FROM company WHERE id =" + companyId;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Company> getByCountry(String country) {
        List<Company> companyList = new ArrayList<>();
        try( PreparedStatement ps = connection.prepareStatement("SELECT * FROM company WHERE country = ?")) {

            ps.setString(1,country);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                companyList.add(getCompanyFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companyList;
    }

    private Company getCompanyFromResultSet(ResultSet resultSet) throws SQLException {
        return new Company(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("country"));
    }

    public void update(Company company) {
        String sql = "UPDATE company SET name = '%s', country = '%s' WHERE id = %d";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format(sql, company.getName(), company.getCountry(), company.getId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
