import com.github.anthropoworphous.cmdlib.command.CMD;
import com.github.anthropoworphous.cmdlib.command.variable.addition.impl.AutoComplete;
import com.github.anthropoworphous.cmdlib.command.variable.single.StringVar;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CMDTest {
    private static class Command implements CMD {
        @Override
        public List<Route> routes() {
            return Routes.of(
                    new Route(
                            (sender, vars) -> {
                                sender.sendMessage((String) vars.get(0));
                                return true;
                            },
                            new StringVar()
                                    .additional(new AutoComplete<>(() -> List.of("aaaaa", "bbbbb", "ccccc")))
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

    @Test
    public void cmdTest() {
        String[] in = new String[]{"aaa"};
        List<String> list = new Command().getAutoComplete(in)
                .flatMap(var -> var.getAutoComplete(in[in.length-1]))
                .toList();
        assert(list.size() == 1);
        assert(list.get(0).equals("aaaaa"));
    }
}
