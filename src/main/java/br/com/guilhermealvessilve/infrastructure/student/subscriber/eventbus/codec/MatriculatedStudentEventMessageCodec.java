package br.com.guilhermealvessilve.infrastructure.student.subscriber.eventbus.codec;

import br.com.guilhermealvessilve.domain.student.event.MatriculatedStudentEvent;
import br.com.guilhermealvessilve.domain.student.vo.CPF;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MatriculatedStudentEventMessageCodec implements MessageCodec<MatriculatedStudentEvent, MatriculatedStudentEvent> {

    public static final String CODEC_NAME;

    static {
        CODEC_NAME = MatriculatedStudentEventMessageCodec.class.getSimpleName();
    }

    @Override
    public void encodeToWire(final Buffer buffer, final MatriculatedStudentEvent event) {

        final var jsonToEncode = new JsonObject()
                .put("studentCpf", event.getStudentCpf().getDocument())
                .put("moment", event.getMoment().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        final var json = jsonToEncode.encode();
        final var length = json.getBytes().length;

        buffer.appendInt(length)
                .appendString(json);
    }

    @Override
    public MatriculatedStudentEvent decodeFromWire(final int pos, final Buffer buffer) {

        final int jsonLength = buffer.getInt(pos);
        final int beginJsonLength = pos + 4;
        final int endJsonLength = beginJsonLength + jsonLength;

        final var jsonStr = buffer.getString(beginJsonLength, endJsonLength);
        final var jsonObject = new JsonObject(jsonStr);

        final var cpf = new CPF(jsonObject.getString("studentCpf"));
        final var moment = LocalDateTime.parse(jsonObject.getString("moment"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        return new MatriculatedStudentEvent(cpf, moment);
    }

    @Override
    public MatriculatedStudentEvent transform(MatriculatedStudentEvent event) {
        return event;
    }

    @Override
    public String name() {
        return CODEC_NAME;
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}