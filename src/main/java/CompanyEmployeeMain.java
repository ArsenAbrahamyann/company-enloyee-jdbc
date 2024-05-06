import manager.CompanyManager;
import manager.EmployeeManager;
import model.Company;
import model.Employee;

import java.util.List;
import java.util.Scanner;

public class CompanyEmployeeMain {

    private static Scanner scanner = new Scanner(System.in);
    private static CompanyManager companyManager = new CompanyManager();
    private static EmployeeManager employeeManager = new EmployeeManager();

    public static void main(String[] args) {
        boolean isRun = true;

        while (isRun) {
            System.out.println("Please input 0 for exit");
            System.out.println("Please input 1 for add Company");
            System.out.println("Please input 2 for add Employee");
            System.out.println("Please input 3 for update company by id");
            System.out.println("Please input 4 for print all companies");
            System.out.println("Please input 5 for print companies by Country");
            System.out.println("Please input 6 for delete company by ID");
            System.out.println("Please input 7 for print all employees");
            System.out.println("Please input 8 for print employees by companyId");
            System.out.println("Please input 9 for delete employee by id");
            String command = scanner.nextLine();
            switch (command) {
                case "0":
                    isRun = false;
                    break;
                case "1":
                    addCompany();
                    break;
                case "2":
                    addEmployee();
                    break;
                case "3":
                    updateCompanyById();
                    break;
                case "4":
                    printAllCompanies();
                    break;
                case "5":
                    printAllCompaniesByCountry();
                    break;
                case "6":
                    deleteCompanyById();
                    break;
                case "7":
                    printAllEmployees();
                    break;
                case "8":
                    printEmployeeByCompanyId();
                    break;
                case "9":
                    deleteEmployeeById();
                    break;
                default:
                    System.out.println("Invalid Command");
            }

        }


    }

    private static void deleteEmployeeById() {
        List<Employee> all = employeeManager.getAll();
        for (Employee employee : all) {
            System.out.println(employee);
        }
        System.out.println("Please choose employee id");
        int employeeId = Integer.parseInt(scanner.nextLine());
        employeeManager.removeById(employeeId);
        System.out.println("Remove Employee");
    }

    private static void printEmployeeByCompanyId() {
        List<Company> all = companyManager.getAll();
        for (Company company : all) {
            System.out.println(company);
        }
        System.out.println("Please choose company id");
        int id = Integer.parseInt(scanner.nextLine());
        Company company = companyManager.getById(id);
        if (company != null) {
            List<Employee> allByCompanyId = employeeManager.getAllByCompanyId(id);
            for (Employee employee : allByCompanyId) {
                System.out.println(employee);
            }
        }
    }

    private static void printAllEmployees() {
        List<Employee> all = employeeManager.getAll();
        for (Employee employee : all) {
            System.out.println(employee);
        }
    }

    private static void deleteCompanyById() {
        List<Company> all = companyManager.getAll();
        for (Company company : all) {
            System.out.println(company);
        }
        System.out.println("Please choose company id");
        int id = Integer.parseInt(scanner.nextLine());
        companyManager.removeById(id);
        System.out.println("Company remove");
    }

    private static void printAllCompaniesByCountry() {
        System.out.println("please input country");
        String country = scanner.nextLine();
        List<Company> byCountry = companyManager.getByCountry(country);
        for (Company company : byCountry) {
            System.out.println(company);
        }
    }

    private static void printAllCompanies() {
        List<Company> all = companyManager.getAll();
        for (Company company : all) {
            System.out.println(company);
        }
    }

    private static void updateCompanyById() {
        List<Company> all = companyManager.getAll();
        for (Company company : all) {
            System.out.println(company);
        }
        System.out.println("Please choose company id");
        int id = Integer.parseInt(scanner.nextLine());
        if (companyManager.getById(id) != null) {
            System.out.println("Please input company name, country");
            String companyStr = scanner.nextLine();
            String[] companyData = companyStr.split(",");
            Company company = new Company();
            company.setId(id);
            company.setName(companyData[0]);
            company.setCountry(companyData[1]);
            companyManager.update(company);
            System.out.println("Company was updated");
        }else {
            System.out.println("Company does not exists");
        }

    }

    private static void addEmployee() {
        List<Company> all = companyManager.getAll();
        for (Company company : all) {
            System.out.println(company);
        }
        System.out.println("Please choose company id");
        int id = Integer.parseInt(scanner.nextLine());
        Company company = companyManager.getById(id);
        if (company != null) {
            System.out.println("please input Employee name, surname, email, companyId");
            String employeeStr = scanner.nextLine();
            String[] employeeData = employeeStr.split(",");
            Employee employee = new Employee();
            employee.setName(employeeData[0]);
            employee.setSurname(employeeData[1]);
            employee.setEmail(employeeData[2]);
            employee.setCompany(company);
            employeeManager.save(employee);
        }
    }

    private static void addCompany() {
        System.out.println("please input company name, country");
        String companyStr = scanner.nextLine();
        String[] companyData = companyStr.split(",");
        Company company = new Company();
        company.setName(companyData[0]);
        company.setCountry(companyData[1]);
        companyManager.save(company);
    }


}




