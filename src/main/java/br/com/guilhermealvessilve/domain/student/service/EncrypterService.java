package br.com.guilhermealvessilve.domain.student.service;

import java.util.concurrent.CompletionStage;

public interface EncrypterService {

    CompletionStage<String> encrypt(String value);

    CompletionStage<Boolean> checkEncrypt(String textValue, String encryptedValue);
}
