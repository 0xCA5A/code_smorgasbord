
################################################################################
# functions
################################################################################
HELP = @grep -B1 '^[a-zA-Z][^:]*:' Makefile |\
      awk 'function p(h,t){printf"%-15s=%s\n",h,t};\
        /\#+/{t=$$0};\
        /:/{gsub(":.*","");h=$$0};\
        /--/{p(h,t);t=h="";};\
        END{p(h,t)}' |\
      sed -n 's/=.*\#+/:/gp'

################################################################################
# variable
################################################################################
COMPILER_FLAGS=-Wall -v

################################################################################
# make target
################################################################################
all: gcc clang help


#+ print this menu
help:
	@echo ---------------------------------------------------------------------
	@echo run target $@
	@echo ---------------------------------------------------------------------
	${HELP}


#+ build with gcc
gcc:
	@echo ---------------------------------------------------------------------
	@echo run target $@
	@echo ---------------------------------------------------------------------
	gcc ${COMPILER_FLAGS} main.c disaster.c -o main_gcc.o
	gcc ${COMPILER_FLAGS} main.c disaster.c -S


#+ build with clang
clang:
	@echo ---------------------------------------------------------------------
	@echo run target $@
	@echo ---------------------------------------------------------------------
	clang ${COMPILER_FLAGS} main.c disaster.c -o main_clang.o
	clang ${COMPILER_FLAGS} main.c disaster.c -S


#+ check source (all methods)
check: check_scan-build check_cppcheck check_flawfinder check_antic


#+ check source with scan-build
check_scan-build:
	@echo ---------------------------------------------------------------------
	@echo run target $@
	@echo ---------------------------------------------------------------------
	scan-build gcc main.c 2>&1 | tee checks/scan-build.log


#+ check source with cppcheck
check_cppcheck:
	@echo ---------------------------------------------------------------------
	@echo run target $@
	@echo ---------------------------------------------------------------------
# 	cppcheck --quiet ./ 2>&1 | tee checks/cppcheck.log
	cppcheck ./ 2>&1 | tee checks/cppcheck.log


#+ check source with flawfinder
check_flawfinder:
	@echo ---------------------------------------------------------------------
	@echo run target $@
	@echo ---------------------------------------------------------------------
	flawfinder ./ | tee checks/flawfinder.log


#+ check source with antic
check_antic:
	@echo ---------------------------------------------------------------------
	@echo run target $@
	@echo ---------------------------------------------------------------------
	antic ./ | tee checks/antic.log


#+ clean project
clean:
	@echo ---------------------------------------------------------------------
	@echo run target $@
	@echo ---------------------------------------------------------------------
	rm -f *.o *.out
	rm -f *.s
	rm -f checks/*.*

