package br.com.xxzidanilloxx.teammanagementapi.service;

import br.com.xxzidanilloxx.teammanagementapi.dto.StudentRequestDTO;
import br.com.xxzidanilloxx.teammanagementapi.dto.StudentResponseDTO;
import br.com.xxzidanilloxx.teammanagementapi.entity.Course;
import br.com.xxzidanilloxx.teammanagementapi.entity.Student;
import br.com.xxzidanilloxx.teammanagementapi.enumeration.Gender;
import br.com.xxzidanilloxx.teammanagementapi.exception.ResourceAlreadyExistsException;
import br.com.xxzidanilloxx.teammanagementapi.exception.ResourceNotFoundException;
import br.com.xxzidanilloxx.teammanagementapi.mapper.StudentMapper;
import br.com.xxzidanilloxx.teammanagementapi.repository.CourseRepository;
import br.com.xxzidanilloxx.teammanagementapi.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentMapper studentMapper;

    @Transactional
    public StudentResponseDTO createStudent(StudentRequestDTO data) {
        validateUniqueFields(data.cpf(), data.email());

        Course course = courseRepository.findById(data.courseId())
                .orElseThrow(() -> new ResourceNotFoundException(Course.class, data.courseId()));

        Student student = Student.toEntity(course, data);
        Student result = studentRepository.save(student);

        return studentMapper.toDto(result);
    }

    @Transactional(readOnly = true)
    public Page<StudentResponseDTO> getAllStudents(Pageable pageable) {
        Page<Student> result = studentRepository.findAll(pageable);
        return result.map(studentMapper::toDto);
    }

    @Transactional(readOnly = true)
    public StudentResponseDTO getStudentById(Long id) {
            Student student = studentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(Student.class, id));
        return studentMapper.toDto(student);
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getStudentByName(String name){
        List<Student> students = studentRepository.searchByName(name);

        if(students.isEmpty()) {
            throw new ResourceNotFoundException("name", name);
        }

        return students.stream().map(studentMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public StudentResponseDTO getStudentByCpf(String cpf) {
        Student student = studentRepository.findByCpf(cpf)
                .orElseThrow(() -> new ResourceNotFoundException("cpf", cpf));
        return studentMapper.toDto(student);
    }

    @Transactional(readOnly = true)
    public StudentResponseDTO getStudentByEmail(String email) {
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("email", email));
        return studentMapper.toDto(student);
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getStudentByGender(String gender) {
        Gender result = Gender.fromString(gender);
        List<Student> students = studentRepository.findByGender(result);

        if (students.isEmpty()) {
            throw new ResourceNotFoundException("gender", gender);
        }

        return students.stream().map(studentMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getStudentByStatus(String status) {
        boolean result = convertStatusToBoolean(status);

        List<Student> students = studentRepository.findByStatus(result);

        if(students.isEmpty()) {
            throw new ResourceNotFoundException("status", status);
        }

        return students.stream().map(studentMapper::toDto).toList();
    }

    @Transactional
    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO data) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Student.class, id));

        if (!data.cpf().equals(student.getCpf()) || !data.email().equals(student.getEmail())) {
            validateUniqueFields(data.cpf(), data.email());
        }

        Course course = courseRepository.findById(data.courseId())
                .orElseThrow(() -> new ResourceNotFoundException(Course.class, data.courseId()));

        student.setFirstName(data.firstName());
        student.setLastName(data.lastName());
        student.setCpf(data.cpf());
        student.setEmail(data.email());
        student.setBirthDate(data.birthDate());
        student.setGender(data.gender());
        student.setCourse(course);
        student.setRa(data.ra());
        student.setStatus(data.status());

        Student result = studentRepository.save(student);
        return studentMapper.toDto(result);
    }

    @Transactional
    public void deactivateStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(Student.class, studentId));
        student.setStatus(false);
        studentRepository.save(student);
    }

    @Transactional
    public void activateStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(Student.class, studentId));
        student.setStatus(true);
        studentRepository.save(student);
    }

    @Transactional
    public StudentResponseDTO updateStudentFields(Long id, StudentRequestDTO data) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Student.class, id));

        if (!data.cpf().equals(student.getCpf()) || !data.email().equals(student.getEmail())) {
            validateUniqueFields(data.cpf(), data.email());
        }

        updateNonNullFields(student, data);

        Student result = studentRepository.save(student);
        return studentMapper.toDto(result);
    }

    @Transactional
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Student.class, id));

        studentRepository.delete(student);
    }

    private void validateUniqueFields(String cpf, String email) {
        boolean cpfExists = studentRepository.existsByCpf(cpf);
        boolean emailExists = studentRepository.existsByEmail(email);

        if (cpfExists && emailExists) {
            throw new ResourceAlreadyExistsException("cpf", cpf, "email", email);
        } else if (cpfExists) {
            throw new ResourceAlreadyExistsException("cpf", cpf);
        } else if (emailExists) {
            throw new ResourceAlreadyExistsException("email", email);
        }
    }

    private void updateNonNullFields(Student student, StudentRequestDTO data) {
        Map<Supplier<Object>, Consumer<Object>> fieldUpdaters = Map.of(
                data::firstName, (value) -> student.setFirstName((String) value),
                data::lastName, (value) -> student.setLastName((String) value),
                data::email, (value) -> student.setEmail((String) value),
                data::birthDate, (value) -> student.setBirthDate((LocalDate) value),
                data::gender, (value) -> student.setGender((Gender) value),
                data::ra, (value) -> student.setRa((String) value),
                data::status, (value) -> student.setStatus((boolean) value)
        );

        fieldUpdaters.forEach((supplier, updater) -> {
            Object value = supplier.get();
            if (value != null) {
                updater.accept(value);
            }
        });

        if (data.courseId() != null) {
            Course course = courseRepository.findById(data.courseId())
                    .orElseThrow(() -> new ResourceNotFoundException(Course.class, data.courseId()));
            student.setCourse(course);
        }
    }

    private boolean convertStatusToBoolean(String status) {
        if ("true".equalsIgnoreCase(status)) {
            return true;
        } else if ("false".equalsIgnoreCase(status)) {
            return false;
        } else {
            throw new ResourceNotFoundException("status", status);
        }
    }
}
