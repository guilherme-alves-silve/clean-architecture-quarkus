package br.com.guilhermealvessilve.domain.student.publisher;

import br.com.guilhermealvessilve.domain.student.event.MatriculatedStudentEvent;

public interface MatriculatedStudentPublisher {

    int submit(final MatriculatedStudentEvent event);
}
