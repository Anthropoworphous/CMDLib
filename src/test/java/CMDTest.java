import com.github.anthropoworphous.cmdlib.command.CMD;
import com.github.anthropoworphous.cmdlib.command.variable.impl.StringVar;

import java.util.List;

public class CMDTest implements CMD {
    @Override
    public List<Route> routes() {
        return Routes.of(
                new Route(
                        (sender, vars) -> {
                            sender.sendMessage((String) vars.get(0));
                            return true;
                        },
                        new StringVar()
                ),

                new Route(
                        (sender, vars) -> {
                            sender.sendMessage("no var supplied");
                            return true;
                        }
                )
        );
    }

    @Override
    public String name() {
        return "cmd";
    }
}
