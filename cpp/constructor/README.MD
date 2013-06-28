the output looks on my machine [0] like this [1].

lesson learned:
 * even unused objects are created. 
   object creation can cause side effects, this is why the compiler can not optimize this away
 * in code like this, default constructor is called first and then the specific one
   
    Crap crap;
    crap = Crap(123);

    two objects are created here, but in general you just want one...


same same, but different:

constructor calls:
* Crap crap(42)
* Crap crap = Crap(14)
* Crap crap(Crap(123))

copy constructor:
* Crap crap(globalDummyCrap)
* Crap crap = globalDummyCrap



[0] nemo
Linux nemo 3.2.0-48-generic #74-Ubuntu SMP Thu Jun 6 19:43:26 UTC 2013 x86_64 x86_64 x86_64 GNU/Linux


[1] main std out

[main.cpp:main:8] create Crap globalDummyCrap(21) variable
	[Crap.cpp:Crap:14] specific constructor with int value (object: 0x7fff9ed0f310)
	[Crap.cpp:setValue:45] set value to the member (object: 0x7fff9ed0f310)

[main.cpp:main:11] start


[main.cpp:main:13] call default constructor, Crap crap
	[Crap.cpp:Crap:8] default constructor (object: 0x7fff9ed0f320)
	[Crap.cpp:~Crap:21] default destructor (object: 0x7fff9ed0f320)

[main.cpp:main:23] call 'default' constructor, Crap crap()
	[!] does not work, compiler complains non-class type Crap()!

[main.cpp:main:35] call specified constructor, Crap crap(42)
	[Crap.cpp:Crap:14] specific constructor with int value (object: 0x7fff9ed0f320)
	[Crap.cpp:setValue:45] set value to the member (object: 0x7fff9ed0f320)
	[Crap.cpp:~Crap:21] default destructor (object: 0x7fff9ed0f320)

[main.cpp:main:45] call specified constructor with assignment, Crap crap = Crap(14)
	[Crap.cpp:Crap:14] specific constructor with int value (object: 0x7fff9ed0f320)
	[Crap.cpp:setValue:45] set value to the member (object: 0x7fff9ed0f320)
	[Crap.cpp:~Crap:21] default destructor (object: 0x7fff9ed0f320)

[main.cpp:main:55] call copy constructor with new object, Crap crap(Crap(123))
	[Crap.cpp:Crap:14] specific constructor with int value (object: 0x7fff9ed0f320)
	[Crap.cpp:setValue:45] set value to the member (object: 0x7fff9ed0f320)
	[Crap.cpp:~Crap:21] default destructor (object: 0x7fff9ed0f320)

[main.cpp:main:65] call copy constructor with existing object, Crap crap(globalDummyCrap)
	[Crap.cpp:Crap:27] copy constructor (object: 0x7fff9ed0f320)
	[Crap.cpp:getValue:54] get value from the member (object: 0x7fff9ed0f310)
	[Crap.cpp:~Crap:21] default destructor (object: 0x7fff9ed0f320)

[main.cpp:main:75] simple assignment, Crap crap = globalDummyCrap
	[Crap.cpp:Crap:27] copy constructor (object: 0x7fff9ed0f320)
	[Crap.cpp:getValue:54] get value from the member (object: 0x7fff9ed0f310)
	[Crap.cpp:~Crap:21] default destructor (object: 0x7fff9ed0f320)

[main.cpp:main:85] assignment, globalDummyCrap = hugeCrap
	[Crap.cpp:Crap:14] specific constructor with int value (object: 0x7fff9ed0f320)
	[Crap.cpp:setValue:45] set value to the member (object: 0x7fff9ed0f320)
	[Crap.cpp:Crap:27] copy constructor (object: 0x7fff9ed0f330)
	[Crap.cpp:getValue:54] get value from the member (object: 0x7fff9ed0f320)
	[Crap.cpp:operator=:34] assignment operator (object: 0x7fff9ed0f310)
	[Crap.cpp:getValue:54] get value from the member (object: 0x7fff9ed0f330)
	[Crap.cpp:setValue:45] set value to the member (object: 0x7fff9ed0f310)
	[Crap.cpp:~Crap:21] default destructor (object: 0x7fff9ed0f330)
	[Crap.cpp:~Crap:21] default destructor (object: 0x7fff9ed0f320)

[main.cpp:main:97] assignment, smallCrap = globalDummyCrap
	[Crap.cpp:Crap:14] specific constructor with int value (object: 0x7fff9ed0f330)
	[Crap.cpp:setValue:45] set value to the member (object: 0x7fff9ed0f330)
	[Crap.cpp:Crap:27] copy constructor (object: 0x7fff9ed0f320)
	[Crap.cpp:getValue:54] get value from the member (object: 0x7fff9ed0f330)
	[Crap.cpp:operator=:34] assignment operator (object: 0x7fff9ed0f330)
	[Crap.cpp:getValue:54] get value from the member (object: 0x7fff9ed0f310)
	[Crap.cpp:setValue:45] set value to the member (object: 0x7fff9ed0f330)
	[Crap.cpp:~Crap:21] default destructor (object: 0x7fff9ed0f320)
	[Crap.cpp:~Crap:21] default destructor (object: 0x7fff9ed0f330)

[main.cpp:main:110] done

	[Crap.cpp:~Crap:21] default destructor (object: 0x7fff9ed0f310)
