package com.github.anthropoworphous.commandlib.adaptor;

import com.github.anthropoworphous.commandlib.arg.ArgsType;
import main.structure.Connected;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Used to connect all the possible args for auto complete
 */
@SuppressWarnings("unused")
public class CMDSupplier {
    /**
     * Start with the 1st arg and go down every possible path
     * @param possibleArgs A list of all value that this connecter will be qualified for
     */
    public CMDSupplier(ArgsType argsType, List<?> possibleArgs) {
        this.possibleArgs = possibleArgs.stream().map(argsType::argTypeToString).collect(Collectors.toList());
    }

    public static Connected<CMDSupplier> newRoot() {
        //argsType doesn't matter, root is not evaluated, this is just used to hold data
        return new Connected<>(new CMDSupplier(ArgsType.STRING, Collections.emptyList()));
    }

    private final List<String> possibleArgs;
    public List<String> getPossibleArgs() {
        return possibleArgs;
    }
}
