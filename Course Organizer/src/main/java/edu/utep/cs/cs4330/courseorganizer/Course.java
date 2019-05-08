package edu.utep.cs.cs4330.courseorganizer;

/**
 * @author Kenneth Ward & Jesus Garcia
 * @version 1.0
 * @since 5/8/2019
 */

/**
 * A class for storing and retrieving information related to a college course
 */
public class Course {
    private String courseTitle;
    private String days;
    private String time;
    private String location;
    private String professorName;
    private String professorPhone;
    private String professorEmail;
    private String professorOfficeLocation;
    private String professorOfficeHours;

    public Course(String courseTitle){
        this.courseTitle = courseTitle;
    }

    public Course(String courseTitle, String days, String time, String location, String professorName,
                  String professorPhone, String professorEmail, String professorOfficeLocation,
                  String professorOfficeHours){


        this.courseTitle = courseTitle;
        this.days = days;
        this.time = time;
        this.location = location;
        this.professorName = professorName;
        this.professorPhone = professorPhone;
        this.professorEmail = professorEmail;
        this.professorOfficeLocation = professorOfficeLocation;
        this.professorOfficeHours = professorOfficeHours;


    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getDays() {
        return days;
    }

    public String getLocation() {
        return location;
    }

    public String getProfessorEmail() {
        return professorEmail;
    }

    public String getProfessorName() {
        return professorName;
    }

    public String getProfessorOfficeHours() {
        return professorOfficeHours;
    }

    public String getProfessorOfficeLocation() {
        return professorOfficeLocation;
    }

    public String getProfessorPhone() {
        return professorPhone;
    }

    public String getTime() {
        return time;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setProfessorEmail(String professorEmail) {
        this.professorEmail = professorEmail;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public void setProfessorOfficeHours(String professorOfficeHours) {
        this.professorOfficeHours = professorOfficeHours;
    }

    public void setProfessorOfficeLocation(String professorOfficeLocation) {
        this.professorOfficeLocation = professorOfficeLocation;
    }

    public void setProfessorPhone(String professorPhone) {
        this.professorPhone = professorPhone;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
