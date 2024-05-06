package manager;

import db.DBConnectionProvider;
import model.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {
    private Connection connection = DBConnectionProvider.getInstance().getConnection();
    private CompanyManager companyManager = new CompanyManager();

    public void save(Employee employee) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO employee (name,surname,email,company_id) " +
                                    "values ('" + employee.getName() + "', '" + employee.getSurname() + "','" + employee.getEmail() + "','" + employee.getCompany().getId() + "',)",Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                employee.setId(generatedKeys.getInt(1));
            }
            System.out.println("employee inserted in to DB");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Employee getById(Integer id) {

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employee where id = " + id);
            if (resultSet.next()) {
                return getEmployeeFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Employee> getAll() {
        List<Employee> employeeList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employee");
            while (resultSet.next()) {
                employeeList.add(getEmployeeFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    private Employee getEmployeeFromResultSet(ResultSet resultSet) throws SQLException {
        return new Employee(resultSet.getInt("id"), resultSet.getString("name"),
                resultSet.getString("surname"), resultSet.getString("email"),
                companyManager.getById(resultSet.getInt("company_id")));
    }

    public  List<Employee> getAllByCompanyId(int companyId) {
        List<Employee> employeeList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employee where company_id =" + companyId);
            while (resultSet.next()) {
                employeeList.add(getEmployeeFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    public void removeById(int employeeId) {
        String sql = "DELETE FROM employee WHERE id =" + employeeId;
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
