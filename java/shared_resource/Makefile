
all: clean compile run

compile:
	javac -g -Xdiags:verbose -Xlint:cast,deprecation,divzero,empty,fallthrough,finally,overrides,path,serial,unchecked *.java

run:
	java SharedResourceAccess

clean:
	rm -f *~
	rm -f *.class
