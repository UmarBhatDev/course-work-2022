package Server.Entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalTime;

@Entity
@Table(name = "task")
public class Task implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "state", length = 50)
    private String state;

    @Column(name = "estimation")
    private Duration estimation;

    @Column(name = "time_spent")
    private Duration timeSpent;

    @Column(name = "description", length = 250)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "assignee_id")
    private Employee assignee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public Board getBoards()
    {
        return board;
    }

    public void setBoards(Board board)
    {
        this.board = board;
    }

    public Employee getAssignee()
    {
        return assignee;
    }

    public void setAssignee(Employee assignee)
    {
        this.assignee = assignee;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Duration getTimeSpent()
    {
        return timeSpent;
    }

    public void setTimeSpent(Duration timeSpent)
    {
        this.timeSpent = timeSpent;
        board.setTimeSpent(board.getTimeSpent().plus(this.timeSpent.minus(timeSpent)));
    }

    public Duration getEstimation()
    {
        return estimation;
    }

    public void setEstimation(Duration estimation)
    {
        this.estimation = estimation;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
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
