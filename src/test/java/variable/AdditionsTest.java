package variable;

import com.github.anthropoworphous.cmdlib.command.variable.Var;
import com.github.anthropoworphous.cmdlib.command.variable.addition.impl.AutoComplete;
import com.github.anthropoworphous.cmdlib.command.variable.addition.impl.Blacklist;
import com.github.anthropoworphous.cmdlib.command.variable.addition.impl.Whitelist;
import com.github.anthropoworphous.cmdlib.command.variable.single.StringVar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        Var<String> var3 = new StringVar().additional(new AutoComplete<>(() -> List.of("aaa", "bbb", "ccc")));
        List<String> list = var3.getAutoComplete("a").toList();
        assert list.size() == 1;
        assert list.get(0).equals("aaa");

        System.out.println("passed auto complete");
    }
}
