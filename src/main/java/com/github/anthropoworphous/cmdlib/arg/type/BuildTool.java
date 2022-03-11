package com.github.anthropoworphous.cmdlib.arg.type;

import main.structure.tree.Connected;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class BuildTool {
    private final List<Connected<ArgType<?>>> tree = new ArrayList<>();

    public RouteBuilder newRoute() {
        return new RouteBuilder(this,  Connected.root());
    }

    public List<Connected<ArgType<?>>> end() { return tree; }


    public static class RouteBuilder {
        public RouteBuilder(BuildTool buildTool, Connected<ArgType<?>> route) {
            this.buildTool = buildTool;
            this.route = route;
            pointer.add(route);
            pointer.add(route);
        }

        private final BuildTool buildTool;
        private final Connected<ArgType<?>> route;

        //0: last edit, 1 & up: entry history
        private final List<Connected<ArgType<?>>> pointer = new ArrayList<>();

        public RouteBuilder add(ArgType<?> argType) {
            Connected<ArgType<?>> a = new Connected<>(argType);
            pointer.set(0, a);
            pointer.get(1).adopt(a);
            return this;
        }
        public RouteBuilder enter() {
            pointer.add(1, pointer.get(0));
            return this;
        }
        public RouteBuilder exit() {
            pointer.remove(0);
            return this;
        }
        public RouteBuilder root() {
            pointer.clear();
            pointer.add(route);
            return this;
        }

        public BuildTool end() {
            buildTool.end().add(route); //end() is just getTree()...
            return buildTool;
        }
    }
}
