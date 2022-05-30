package variable;

import com.github.anthropoworphous.cmdlib.command.variable.Var;
import com.github.anthropoworphous.cmdlib.command.variable.addition.impl.AutoComplete;
import com.github.anthropoworphous.cmdlib.command.variable.addition.impl.Blacklist;
import com.github.anthropoworphous.cmdlib.command.variable.addition.impl.Whitelist;
import com.github.anthropoworphous.cmdlib.command.variable.impl.StringVar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

public class AdditionsTest {
    @Test
    public void test() {
        // Whitelist
        Var<String> var1 = new StringVar().additional(new Whitelist<>(() -> List.of("a", "b", "c")));
        var1.get(new Var.Input("a"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> var1.get(new Var.Input("d")));
        System.out.println("passed whitelist");

        // Blacklist
        Var<String> var2 = new StringVar().additional(new Blacklist<>(() -> List.of("d", "e", "f")));
        var2.get(new Var.Input("a"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> var2.get(new Var.Input("d")));
        System.out.println("passed blacklist");

        // AutoComplete
        Var<String> var3 = new StringVar().additional(new AutoComplete<>(input ->
                Stream.of("aaa", "bbb", "ccc")
                        .filter(str -> str.startsWith(input))
                        .toList()
        ));
        var list = var3.autoComplete("a");
        assert list.size() == 1;
        assert list.get(0).equals("aaa");

        System.out.println("passed auto complete");
    }
}
