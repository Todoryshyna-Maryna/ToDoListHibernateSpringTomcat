package toDoList.core.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import toDoList.core.model.Task;
import toDoList.core.model.TaskList;

import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;


/**
 * Created by employee on 11/24/16.
 */
@Repository("taskList")
public class TaskListDaoImpl implements TaskListDao {
    @Autowired
    private SessionFactory sessionFactory;

    public List<TaskList> getAll() {
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(TaskList.class);
        List<TaskList> taskLists = (List<TaskList>) criteria.list();
        return taskLists;
    }

    public void create(String name) {
        sessionFactory.getCurrentSession().save(new TaskList(name));
    }

    public void delete(String listId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(TaskList.class)
                .add(eq("id", Integer.parseInt(listId)));
        TaskList taskLists = (TaskList) criteria.uniqueResult();
        for (Task task : taskLists.getAllTasks()) {
            session.delete(task);
        }
        session.delete(taskLists);
    }

    public void update(String listId, String name) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(TaskList.class)
                .add(eq("id", Integer.parseInt(listId)));
        TaskList taskList = (TaskList) criteria.uniqueResult();
        taskList.setName(name);
        session.update(taskList);
    }
}
