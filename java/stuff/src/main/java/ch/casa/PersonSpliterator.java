package ch.casa;

import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Reads {@link Person} data object by object
 */
public class PersonSpliterator implements Spliterator<Person> {

    private String name;
    private Person.Sex sex;
    private int age;
    private String country;

    private Spliterator<String> baseSpliterator;

    public PersonSpliterator(Spliterator<String> baseSpliterator) {
        this.baseSpliterator = baseSpliterator;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Person> action) {
        if (baseSpliterator.tryAdvance(n -> name = n) &&
            baseSpliterator.tryAdvance(s -> this.sex = Person.Sex.valueOf(s)) &&
            baseSpliterator.tryAdvance(a -> this.age = Integer.parseInt(a)) &&
            baseSpliterator.tryAdvance(c -> this.country = c)) {

            action.accept(new Person(this.name, this.sex, this.age, this.country));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Spliterator<Person> trySplit() {

        // Parallel operations not supported
        return null;
    }

    @Override
    public long estimateSize() {
        // 4: Number of object properties
        return baseSpliterator.estimateSize() / 4;
    }

    @Override
    public int characteristics() {
        return baseSpliterator.characteristics();
    }
}
