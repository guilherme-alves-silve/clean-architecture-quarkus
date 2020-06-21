package br.com.guilhermealvessilve.domain.student.service;

import java.util.concurrent.CompletionStage;

public interface EncrypterService {

    CompletionStage<String> encrypt(byte[] value);

    CompletionStage<Boolean> checkEncrypt(byte[] textValue, String encryptedValue);
}
