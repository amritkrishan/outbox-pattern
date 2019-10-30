package com.student.service;

import com.student.dto.EmailChangeDTO;
import com.student.dto.EnrollStudentDTO;
import com.student.dto.StudentDTO;

public interface StudentService {

    /**
     * Gets the Student Details for the given StudentId.
     *
     * @param studentId
     * @return StudentDTO
     */
    StudentDTO getStudent(Integer studentId) throws Exception;

    /**
     * Enrolls the Student.
     *
     * @param student
     * @return StudentDTO
     */
    StudentDTO enrollStudent(EnrollStudentDTO student) throws Exception;

    /**
     * Updates the Student Email for the given studentId.
     *
     * @param studentId
     * @param studentEmail
     * @return StudentDTO
     */
    StudentDTO updateStudentEmail(Integer studentId, EmailChangeDTO studentEmail) throws Exception;
}