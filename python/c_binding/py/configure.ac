AC_PREREQ([2.68])
AC_INIT([pvnrt], [1], [/dev/null])
AC_CONFIG_SRCDIR([ideal.c])
AC_CONFIG_HEADERS([config.h])

AM_INIT_AUTOMAKE
AC_PROG_CC_C99
LT_INIT

#check for python, setting a PYTHON variable
#to : . if it does not exist add a HAVE_PYTHON variable appropriate
AM_PATH_PYTHON(,, [:])
AM_CONDITIONAL([HAVE_PYTHON], [test "$PYTHON" != :])

#if PYTHON is set, autoconf will continue in subdir py, else
#it will ignore this subdir
if test "$PYTHON" != : ; then
AC_CONFIG_SUBDIRS([py])
fi

#turn Makefile.am into a makefile
AC_CONFIG_FILES([Makefile py/Makefile])
AC_OUTPUT