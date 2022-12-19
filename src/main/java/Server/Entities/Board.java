package Server.Entities;


import javax.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
public class Board implements Serializable
{
    public Board(){  }
    
    public Board(String name, LocalDate startDate, LocalDate endDate)
    {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "time_spent")
    private Duration timeSpent;

    @OneToMany(mappedBy = "board",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Task> tasks;

    @ManyToMany(mappedBy = "boards",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Employee> employees;
    

    public List<Task> getTasks()
    {
        return tasks;
    }

    public void setTasks(List<Task> tasks)
    {
        this.tasks = tasks;
    }

    public void setEmployees(List<Employee> employees)
    {
        this.employees = employees;
    }
    
    public List<Employee> getEmployees()
    {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees)
    {
        this.employees = employees;
    }

    public Duration getTimeSpent()
    {
        return timeSpent;
    }

    public void setTimeSpent(Duration timeSpent)
    {
        this.timeSpent = timeSpent;
    }

    public LocalDate getEndDate()
    {
        return endDate;
    }

    public void setEndDate(LocalDate endDate)
    {
        this.endDate = endDate;
    }

    public LocalDate getStartDate()
    {
        return startDate;
    }

    public void setStartDate(LocalDate startDate)
    {
        this.startDate = startDate;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }
}
