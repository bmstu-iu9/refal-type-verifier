package VerificatorInterpritator.Tokens;

public class TypeRef extends SimpleType{
    private Mode mode;
    private String name;
    private Type ref;

    public TypeRef(String value) {
        setMode(value.charAt(0));
        this.name = value.substring(2);
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(char mode) {
        switch (mode) {
            case 's':
            case 'S':
                this.mode = Mode.S;
                break;
            case 't':
            case 'T':
                this.mode = Mode.T;
                break;
            case 'e':
            case 'E':
                this.mode = Mode.E;
                break;
        }
    }

    public Type getRef() {
        return ref;
    }

    public void setRef(Type ref) {
        this.ref = ref;
    }

    @Override
    public String toString() {
        return "TypeRef{" +
                "mode=" + mode +
                ", name='" + name + '\'' +
                ", ref=" + ref +
                '}';
    }
}
