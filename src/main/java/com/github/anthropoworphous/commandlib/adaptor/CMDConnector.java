package com.github.anthropoworphous.commandlib.adaptor;

import com.github.anthropoworphous.commandlib.arg.ArgsType;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Used to connect all the possible args for auto complete
 */
@SuppressWarnings("unused")
public class CMDConnector {
    /**
     * Start with the 1st arg and go down every possible path
     * @param possibleArgs A list of all value that this connecter will be qualified for
     */
    public CMDConnector(ArgsType argsType, List<?> possibleArgs) {
        this.possibleArgs = possibleArgs.stream().map(argsType::argTypeToString).collect(Collectors.toList());
    }

    private final List<String> possibleArgs;
    private final List<CMDConnector> childs = new ArrayList<>();
    private CMDConnector parent;

    public List<String> match(List<String> args, int lv) {
        Bukkit.getConsoleSender().sendMessage("LV: " + lv + ", value: " + args.get(lv));
        if (args.size()-1 == lv) {
            return possibleArgs.stream()
                    .filter(arg -> arg.startsWith(args.get(lv)))
                    .collect(Collectors.toList());
        } else {
            return childs.stream()
                    .filter(child -> child.parent.possibleArgs.stream()
                            .anyMatch(arg -> arg.startsWith(args.get(lv))))
                    .map(child -> child.match(args, lv+1))
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        }
    }

    public CMDConnector addChild(@NotNull CMDConnector child) {
        childs.add(child.addParent(this));
        return this;
    }
    private CMDConnector addParent(CMDConnector parent) {
        this.parent = parent;
        return this;
    }
}
