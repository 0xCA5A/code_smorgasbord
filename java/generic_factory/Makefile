
all: clean compile run

compile:
	javac -Xlint:cast,deprecation,divzero,empty,fallthrough,finally,overrides,path,serial,unchecked *.java

run:
	java GenericFactory

clean:
	rm -f *~
	rm -f *.class
