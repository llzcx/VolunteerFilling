package com.social.demo.demo;

import java.util.*;

/**
 * @author 周威宇
 */
public class FirstChoice {
    static HashMap<School,List<Student>> schoolAdmissions;
    public HashMap<School, List<Student>> volunteerMatching(List<Student> students,List<School> schools){
        for (School school : schools) {
            List<Student> students1 = new ArrayList<>();
            schoolAdmissions.put(school,students1);
        }
        students.sort((s1, s2) -> s2.getScore() - s1.getScore());
        int volunteersNumber = students.get(0).volunteerList.size();
        for(int i=0;i<volunteersNumber;i++){
            for (Student student : students) {
                if(!student.getAdmissionStatus()){
                    School school = student.getVolunteerList().get(i);
                    if(!school.getAdmissionStatus()){
                        List<Student> students1 = schoolAdmissions.get(school);
                        if(students1.size()==school.getEnrollmentNumber()){
                            school.setAdmissionStatus(false);
                        }else{
                            student.setAdmissionStatus(true);
                            students1.add(student);
                        }
                    }
                }
            }
        }
        return schoolAdmissions;
        }
}
