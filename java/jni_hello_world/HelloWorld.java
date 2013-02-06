
// source
// http://192.9.162.55/docs/books/jni/html/start.html


class HelloWorld
{

    private native void print();

    public static void main(String[] args) {
        new HelloWorld().print();
    }

    static {
        //load libHelloWorld.so
        System.loadLibrary("HelloWorld");
    }

}