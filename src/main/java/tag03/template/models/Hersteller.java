package tag03.template.models;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Hersteller {
    public static final Map<Integer, Hersteller> herstellers = new HashMap<>();

    private final int nummer;
    private String name;

    public Hersteller(int nummer, String name) {
        this.nummer = nummer;
        this.name = name;

        herstellers.put(nummer, this);
    }

    public int getNummer() {
        return nummer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Hersteller.class.getSimpleName() + "[", "]")
                .add("nummer=" + nummer)
                .add("name='" + name + "'")
                .toString();
    }
}
