package Server;

import Server.Entities.Employee;

import java.io.Serializable;
import java.util.List;

public class TransferMessageContainer implements Serializable 
{
    private final String key;
    private final String message;

    public EmployeeList EmployeeList;
    public Employee Employee;

    public TransferMessageContainer(String Key, String message)
    {
        this.key = Key;
        this.message = message;
    }

    public String Key() { return key;}
    public String message() { return message;}


    @Override
    public String toString() {
        return "TransferMessageContainer{" +
                "message='" + message + '\'' +
                '}';
    }

    public static class EmployeeList implements Serializable
    {
        public EmployeeList(List<Employee> employeeList) { this.value = employeeList; }
        public List<Employee> value;
    }
}
