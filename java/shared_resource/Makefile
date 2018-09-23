JAVAC_FLAGS = -g -Xdiags:verbose -Xlint:cast,deprecation,divzero,empty,fallthrough,finally,overrides,path,serial,unchecked
TARGET_DIR = target

all: clean prepare compile run

prepare:
	mkdir -p target

compile:
	javac -d $(TARGET_DIR) $(JAVAC_FLAGS) src/main/java/*.java

run:
	cp src/main/resources/*.* $(TARGET_DIR)
	java --class-path target src.main.java.SharedResourceAccess

clean:
	rm -rf target
