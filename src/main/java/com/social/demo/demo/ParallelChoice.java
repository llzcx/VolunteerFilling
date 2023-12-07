package com.social.demo.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 周威宇
 */
public class ParallelChoice {
    static HashMap<School, List<Student>> schoolAdmissions;
    public HashMap<School, List<Student>> volunteerMatching(List<Student> students,List<School> schools){
        for (School school : schools) {
            List<Student> students1 = new ArrayList<>();
            schoolAdmissions.put(school,students1);
        }
        students.sort((s1, s2) -> s2.getScore() - s1.getScore());
        for(Student student : students){
            List<School> schools1 = student.getVolunteerList();
            for(School school : schools1){
            if(!school.getAdmissionStatus()){
                List<Student> students1 = schoolAdmissions.get(school);
                student.setAdmissionStatus(true);
                students1.add(student);
                if(students1.size()==school.getEnrollmentNumber()){
                    school.setAdmissionStatus(true);
                }
            }
            }
        }
        return schoolAdmissions;
    }
}
