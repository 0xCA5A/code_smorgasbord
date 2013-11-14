public class Application {

    public static void main(String[] args) {
        System.out.println("[i] hello from main");

        GenericSuperClass<String> object0 = new GenericSubClass<String>("object0_string");
        object0.sayHello();

        GenericSuperClass<Integer> object1 = new GenericSubClass<Integer>("object1_integer");
        object1.sayHello();

        GenericSuperClass<Float> object2 = new GenericSubClass<Float>("object2_float");
        object2.sayHello();

        GenericSuperClass.printRegisteredObjects();
    }
}