package VerificatorInterpritator.Tokens;

public class VarTermType extends TermType{
    private Mode mode;
    private String name;
    private Type ref;

    public VarTermType(String value) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getRef() {
        return ref;
    }

    public void setRef(Type ref) {
        this.ref = ref;
    }

    @Override
    public String toString() {
        return "" + mode +
                "." + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VarTermType that = (VarTermType) o;

        if (mode != that.mode) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return ref != null ? ref.equals(that.ref) : that.ref == null;
    }

    @Override
    public int hashCode() {
        int result = mode.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (ref != null ? ref.hashCode() : 0);
        return result;
    }
}
