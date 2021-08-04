import ch.casa.Person;
import ch.casa.PersonSpliterator;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class PersonSpliteratorTest {

    @Test
    void spliterator_when_wellDefined_then_objectsCreated() {
        final InputStream is = getClass().getResourceAsStream("persons.txt");

        try (final Stream<String> lines = new BufferedReader(new InputStreamReader(is, Charset.defaultCharset())).lines()) {

            final Spliterator<String> baseSpliterator = lines.spliterator();
            final Spliterator<Person> personSpliterator = new PersonSpliterator(baseSpliterator);

            final Map<String, List<Person>> byCountry = StreamSupport.stream(personSpliterator, false)
                .collect(Collectors.groupingBy(Person::getCountry));
            System.out.println(byCountry);
            System.out.println(byCountry.size());
        }
    }
}
