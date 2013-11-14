import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class GenericSubClass<T> extends GenericSuperClass<T> {

    GenericSubClass(String objectIdentifier) {
        super(objectIdentifier);
    }

    public void sayHello() {
        System.out.println("[GenericSubClass] hello from " + getObjectIdentifier());
    }
}