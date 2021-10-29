package com.github.anthropoworphous.commandlib;

import com.github.anthropoworphous.commandlib.arg.Args;
import com.github.anthropoworphous.commandlib.arg.ArgsLimiter;
import com.github.anthropoworphous.commandlib.cmd.CMD;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class ExampleCMD extends CMD {
    @Override
    public Boolean execute(CommandSender sender, Args args) {
        //uses arg.get(<index>) with (int) cast
        for (int index = 0; index <= (int) args.get(0); index++) {
            //uses arg.getString() so no need to cast
            Bukkit.getServer().getConsoleSender().sendMessage(args.getString(1));
        }
        return true;
    }

    @Override
    public List<ArgsLimiter<?>> argLimiters() {

        return Arrays.asList(
                //limit args type, this one limit the first arg to 1, 69 or 420
                new ArgsLimiter<>(ArgsLimiter.ArgsType.INT,
                        Arrays.asList(1, 69, 420)),
                //this one limit the second arg to any String besides "owo", "uwu" and "cum" (whitelist off = blacklist)
                new ArgsLimiter<>(ArgsLimiter.ArgsType.STRING,
                        Arrays.asList("owo", "uwu", "yiff"),
                        false),
                //this one limit the third arg to boolean so only true or false
                new ArgsLimiter<>(ArgsLimiter.ArgsType.BOOLEAN)

                /*if command sender input args that's not allowed, the usage will be send to command sender
                by default the usage format is:
                /<cmdName> [<1st argsLimitor's type if it exist> <2nd argsLimitor's type if it exist>...etc]
                    <1st argsLimitor's type if it exist> <1st argsLimitor's type's examples>
                        <1st argsLimitor's 1st limit if it exist>
                        <1st argsLimitor's 2nd limit if it exist>
                        <1st argsLimitor's 3rd limit if it exist>
                        <1st argsLimitor's 4th...you get the idea>
                    <2nd argsLimitor's type if it exist> <2nd argsLimitor's type's examples>
                        <1st argsLimitor's 1st limit if it exist>
                        <1st argsLimitor's 2nd limit...you get it>
                */
        );
    }

    @Override
    public String cmdName() {
        //the command will be "/example-command
        return "example-command";
    }
}
