package Server.PersistentProvider;

import Server.Entities.Employee;
import Server.Entities.User;

import java.util.List;

public interface PersistentProvider {
    User getUserByLogin(String login);
    boolean isUserExist(String login);

    Employee getEmployeeByLogin(String login);

    void updateEmployee(Employee employee);

    List<Employee> allEmployee();
    void updateUser(User user);

    <TRecord> boolean add(TRecord record);
}
