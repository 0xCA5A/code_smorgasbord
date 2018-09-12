import java.util.logging.Logger;

// That does not work at all
class DummyFactory<T> {
    private Class<T> classOfT;

    public T getInstance() throws InstantiationException, IllegalAccessException {

        //return new T(); // won't compile
        //return T.newInstance(); // won't compile
        //return T.class.newInstance(); // won't compile
        return classOfT.newInstance();
    }
}


// That seems to work fine
class Factory2<T> {

    // T type MUST have a default constructor
    private final Class<T> type;

    public Factory2(Class<T> type) {
        this.type = type;
    }

    public T getInstance() {
        try {
            // assume type is a public class
            return type.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <A> Factory2<A> getInstance(Class<A> type) {
        return new Factory2<A>(type);
    }
}

class Base {
    protected Logger logger;

    Base() {
        logger = Logger.getLogger(getClass().getName());
    }

    protected String getHelloMsg(String className, Object identifier, int cnt) {
        return String.format("Hello from new %s: %s (instance #%d)", className, identifier, cnt);
    }

}

class A extends Base {
    private static int cnt;
    A() {
        super();
        logger.info(getHelloMsg(getClass().getName(), this, cnt));
        cnt++;
    }
}

class B extends Base {
    private static int cnt;
    B() {
        super();
        logger.info(getHelloMsg(getClass().getName(), this, cnt));
        cnt++;
    }
}

class C extends Base {
    private static int cnt;
    C() {
        super();
        logger.info(getHelloMsg(getClass().getName(), this, cnt));
        cnt++;
    }
}


class GenericFactory {

    private Logger logger;

    GenericFactory() {
        logger = Logger.getLogger(getClass().getName());
    }

    public void run() {

        // Does not work
        A a = new A();
        DummyFactory<A> dummyFactory = new DummyFactory<A>();
        try {
            a = dummyFactory.getInstance();
        } catch (Exception e) {
            logger.warning(String.format("Caught exception: %s", e));
        }
        logger.info("The object: " + a);

        // Works fine!
        Object obj;
        Factory2<B> bFactory = Factory2.getInstance(B.class);
        Factory2<C> cFactory = Factory2.getInstance(C.class);
        for (int i = 0; i < 10; i++) {
            obj = bFactory.getInstance();
            obj = cFactory.getInstance();
        }
    }

    public static void main(String[] args) {
        GenericFactory genf = new GenericFactory();
        genf.run();
    }
}
