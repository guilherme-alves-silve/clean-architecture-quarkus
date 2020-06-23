package br.com.guilhermealvessilve.shared.util;

import io.quarkus.runtime.configuration.ProfileManager;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Profile {

    private static final String TEST =  "test";

    public static boolean isTestProfile() {
        return TEST.equalsIgnoreCase(ProfileManager.getActiveProfile());
    }
}
