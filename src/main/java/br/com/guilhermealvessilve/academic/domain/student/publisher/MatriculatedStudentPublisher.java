package br.com.guilhermealvessilve.academic.domain.student.publisher;

import br.com.guilhermealvessilve.academic.domain.student.event.MatriculatedStudentEvent;

public interface MatriculatedStudentPublisher {

    int submit(final MatriculatedStudentEvent event);
}
