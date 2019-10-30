package com.student.dto;

import com.student.dao.entity.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    StudentDTO studentEntityToDTO(StudentEntity studentEntity);

    StudentEntity studentDTOToEntity(EnrollStudentDTO dto);
}