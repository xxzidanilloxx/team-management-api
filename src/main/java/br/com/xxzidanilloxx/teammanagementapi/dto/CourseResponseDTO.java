package br.com.xxzidanilloxx.teammanagementapi.dto;

import java.util.List;

public record CourseResponseDTO(
        Long id,
        String name,
        String alias,
        List<StudentFromCourseResponseDTO> students) {
}
