import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class GenericSuperClass<T> {

    static List<String> myObjectList = new ArrayList<String>();
    private String m_objectIdentifier;

    GenericSuperClass(String objectIdentifier) {
        this.m_objectIdentifier = objectIdentifier;
        registerObject();
    }

    public void sayHello() {
        System.out.println("[GenericSubClass] hello from " + getObjectIdentifier());
    }

    private void registerObject() {
        System.out.println("[GenericSuperClass] register object " + m_objectIdentifier);
        myObjectList.add(m_objectIdentifier);
    }

    public String getObjectIdentifier() {
        return m_objectIdentifier;
    }

    public static void printRegisteredObjects() {
        System.out.println("[GenericSuperClass] registered objects:");
        for (int i = 0; i < myObjectList.size(); ++i) {
            System.out.println(" * " + myObjectList.get(i));
        }
        System.out.println("");
    }
}