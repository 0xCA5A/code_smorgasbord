in the beginning i just wanted to check why the XOR byte swap [1] is slower in my jni hello world test [2]...
then it overshoot a little bit to this stuff here.

[1] http://en.wikipedia.org/wiki/XOR_swap_algorithm

[2] https://github.com/0xCA5A/code_smorgasbord/tree/master/java/jni_hello_world


lesson learned
--------------
* http://stackoverflow.com/questions/495021/why-can-templates-only-be-implemented-in-the-header-file

be careful with static stuff and templates!

my algorithm register code did not work at all, the algorithms are registered by template type.
this makes sense, the compiler creates a class for each type.
the static stuff is contatined in each class.

code:
```
MemoryFlipperAlgorithm<uint32_t>::printRegisteredAlgorithms();
MemoryFlipperAlgorithm<uint8_t>::printRegisteredAlgorithms();
```

stdout:
```
sam@guido:~/projects/github/code_smorgasbord/cplusplus/native_memory_flipper$ ./memoryFlipperApplication
static void MemoryFlipperAlgorithm<T>::printRegisteredAlgorithms() [with T = unsigned int][INFO]: registered algorithms:
static void MemoryFlipperAlgorithm<T>::printRegisteredAlgorithms() [with T = unsigned char][INFO]: registered algorithms:
 * memoryFlipperAlgorithmXOR
```

in java, it is possible to have static members in generic classes.
https://github.com/0xCA5A/code_smorgasbord/tree/master/java/hello_java_generics


with optimization, the flipper runs factor 4 faster.
choosen approach with only one portion of data per function call not optimal.
a better (optimizable) solution is to pass wider buffers to the flip function.
the compiler can then optimize if possible (simd stuff).
i think it is as well not optimal to use a function pointer for the algorithm here.
as far as i know the compile can not fully optimize this (inline).

