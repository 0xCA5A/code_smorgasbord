# hello jni world
this is a test application to verify the jni performance for a simple byte flip (byte array).
evaluation is splitted in 3 applications, i have to fix this.

run linke this (in root directory):
 * $ make
 * $ ./run_test_n_times.sh 512
 * $ ./do_stat.py byteflipper.log

first line compiles the java code as well as the native c implementation (shared object).
second line runs the java application n times. log is per default written to file byteflipper.log.
third line evaluates the log file and prints some stats.

if you have a lot of log files you can do
 * $ for logfile in $(ls *.log); do ./do_stat.py $logfile; done


# todo
 * fixed array size in java code
 * check if jni array acces is optimal
 * implement byte shift function in java
 * check jna
 * integrate shell script in python (subprocess)
 * check if the algs work with odd nr of bytes...

# my test system
 * Intel(R) Core(TM)2 CPU         T5600  @ 1.83GHz
 * ubuntu 12.04.2 64bit
 * java version 1.6.0_24
  * OpenJDK Runtime Environment (IcedTea6 1.11.5) (6b24-1.11.5-0ubuntu1~12.04.1)
  * OpenJDK 64-Bit Server VM (build 20.0-b12, mixed mode)


## links
 * http://192.9.162.55/docs/books/jni/html/objtypes.html, 3.3.2 Accessing Arrays of Primitive Types
 * https://github.com/twall/jna#readme
 * http://en.gentoo-wiki.com/wiki/Safe_Cflags
