package com.github.anthropoworphous.cmdlib.arg;

public abstract class MultiValueType {
    /*
    vector
    location
    location + angle

    MultiValueType(String readableName, String readableDescription, ArgType<?, ?>... base) {
        this.readableName = readableName;
        this.readableDescription = readableDescription;
    }

    private final String readableName;
    private final String readableDescription;

    @Nullable
    public abstract <T> T assemble(Object... objs);

    @NotNull public abstract List<IConnectable> constructLimiter();

    @Override
    public String getReadableName() {
        return readableName;
    }
    @Override
    public String getReadableDescription() {
        return readableDescription;
    }

    @Override
    @NotNull
    public String argTypeToString(Object input) { return String.valueOf(input); }
    */
}
