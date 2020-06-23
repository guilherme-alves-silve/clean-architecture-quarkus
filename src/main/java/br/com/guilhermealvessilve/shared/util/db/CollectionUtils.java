package br.com.guilhermealvessilve.shared.util.db;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Optional;

@UtilityClass
public class CollectionUtils {

    public static <E> Optional<E> getOptionalFromSingleton(List<E> singleton) {

        if (singleton.size() > 1) {
            throw new IllegalArgumentException("More than one result of singleton list!");
        }

        return (singleton.isEmpty())
                ? Optional.empty()
                : Optional.of(singleton.get(0));
    }
}
