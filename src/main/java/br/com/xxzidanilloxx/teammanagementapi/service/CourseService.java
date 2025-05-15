package br.com.xxzidanilloxx.teammanagementapi.service;

import br.com.xxzidanilloxx.teammanagementapi.dto.CourseRequestDTO;
import br.com.xxzidanilloxx.teammanagementapi.dto.CourseResponseDTO;
import br.com.xxzidanilloxx.teammanagementapi.entity.Course;
import br.com.xxzidanilloxx.teammanagementapi.exception.DeletionNotAllowedException;
import br.com.xxzidanilloxx.teammanagementapi.exception.ResourceAlreadyExistsException;
import br.com.xxzidanilloxx.teammanagementapi.exception.ResourceNotFoundException;
import br.com.xxzidanilloxx.teammanagementapi.mapper.CourseMapper;
import br.com.xxzidanilloxx.teammanagementapi.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Transactional
    public CourseResponseDTO createCourse(CourseRequestDTO data) {
        duplicateCourseName(data.name());
        Course course = Course.toEntity(data);
        Course result = courseRepository.save(course);
        return courseMapper.toDto(result);
    }

    @Transactional(readOnly = true)
    public List<CourseResponseDTO> getAllCourses() {
        List<Course> result = courseRepository.findAll();
        return result.stream().map(courseMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public CourseResponseDTO getCourseById(Long id) {
        Course result = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Course.class, id));
        return courseMapper.toDto(result);
    }

    @Transactional(readOnly = true)
    public List<CourseResponseDTO> getCourseByName(String name) {
        List<Course> courses = courseRepository.searchByName(name);

        if(courses.isEmpty()) {
            throw new ResourceNotFoundException("name", name);
        }

        return courses.stream().map(courseMapper::toDto).toList();
    }

    @Transactional
    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO data) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Course.class, id));
        duplicateCourseNameForUpdate(data.name(), id);
        course.setName(data.name());
        course.setAlias(data.alias());
        Course result = courseRepository.save(course);
        return courseMapper.toDto(result);
    }

    @Transactional
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Course.class, id));
        if (!course.getStudents().isEmpty()) {
            throw new DeletionNotAllowedException();
        }
        courseRepository.delete(course);
    }

    private void duplicateCourseName(String name) {
        boolean courseExists = courseRepository.existsByName(name);
        if (courseExists) {
            throw new ResourceAlreadyExistsException(Course.class, name);
        }
    }

    private void duplicateCourseNameForUpdate(String name, Long id) {
        boolean courseExists = courseRepository.existsByNameAndIdNot(name, id);
        if (courseExists) {
            throw new ResourceAlreadyExistsException(Course.class, name);
        }
    }
}