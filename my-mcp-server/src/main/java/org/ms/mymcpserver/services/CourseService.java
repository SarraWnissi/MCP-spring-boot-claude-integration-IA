package org.ms.mymcpserver.services;
import jakarta.annotation.PostConstruct;
import org.ms.mymcpserver.model.Course;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
public class CourseService {
    List<Course> courses=new ArrayList<>();
    @PostConstruct
    public void init(){
        courses.add(new
                Course("Flutter","https://www.youtube.com/watch?v=DvAq5dKN5uk&list=PLzFUEeW dXH-0xB7f8dxMCcwZKIdLaLMRL"));
                courses.add(new Course("Artificial Itelligence","https://www.youtube.com/watch?v=aV8tqlTp0AE&list=PLbVeJQ3E0gl 2HZnKZGMDjwqS67Dqb2KVv"));
        courses.add(new
                Course("Docker","https://www.youtube.com/watch?v=CPS5yXzLBwQ&list=PLxr551TU smApVwBMzhtLqrWqcKQs4sh19"));
    }
    @Tool(name="get_all_courses", description="Get all courses of ProfessorMohamed Youssfi")
    public List<Course> getAllCourses(){
        return courses;
    }
    @Tool(name="get_course_by_title", description="Get a course by title ofProfessor Mohamed Youssfi")
    public Course getCourseById(String title){
        return courses.stream().filter(c->c.title().equals(title)).findFirst().orElse(null);
    }
}