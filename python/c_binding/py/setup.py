from distutils.core import setup, Extension

#specification of the production process for python's distutils

py_modules = ['pvnrt']

Emodule = Extension('pvnrt',
            libraries = ['pvnrt'],      #sources and linker flags, send -lpvnrt to linker
            library_dirs = ['..'],      #add -L.. to the linker's flags
            sources = ['ideal.py.c'])   #source, as in automake

setup ( name = 'pvnrt',
        version = '1.0',
        description = 'pressure * volume = n * R * Temperature',
        ext_modules = [Emodule])