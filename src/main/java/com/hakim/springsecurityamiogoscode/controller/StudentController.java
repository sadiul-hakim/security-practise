package com.hakim.springsecurityamiogoscode.controller;

import com.hakim.springsecurityamiogoscode.entity.Student;
import com.hakim.springsecurityamiogoscode.exception.ResourceNotFoundException;
import com.hakim.springsecurityamiogoscode.payload.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {
    private static List<Student> studentList=List.of(
            Student.builder().id(1).name("Hakim").build(),
            Student.builder().id(2).name("Ashik").build(),
            Student.builder().id(3).name("Saikot").build()
    );
    @GetMapping("/{studentId}")
    public Student getStudent(@PathVariable Integer studentId){
        return studentList.stream()
                .filter(student -> Objects.equals(student.getId(), studentId))
                .findFirst()
                .orElseThrow(()->new ResourceNotFoundException("Could not get student with id "+studentId));
    }
    @PostMapping("/")
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        List<Student> students=new ArrayList<>(studentList);
        students.add(student);
        studentList= Collections.unmodifiableList(students);

        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<ApiResponse> deleteStudent(@PathVariable Integer studentId){
        List<Student> students=new ArrayList<>(studentList);
        Student student=students.stream()
                .filter(s -> Objects.equals(s.getId(), studentId))
                .findFirst()
                .orElseThrow(()->new ResourceNotFoundException("Could not get student with id "+studentId));;
        students.remove(student);

        studentList= Collections.unmodifiableList(students);

        return ResponseEntity.ok(new ApiResponse(String.format("Student %d is deleted successfully",studentId),true));
    }

}
