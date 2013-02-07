#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""script to parse test application output.
expect something like this n times:

################################################################################
# [main] configuration
# * BYTE_ARRAY_SIZE: 4096
################################################################################

################################################################################
# [main] empty JNI calls
################################################################################
[main] time used in function emptyCallJNI(): 16064ns
[main] time used in function emptyCallGetAndReleaseByteArrayElementsJNI(): 21301ns
[main] time used in function emptyCallGetAndReleasePrimitiveArrayCriticalJNI(): 17461ns

################################################################################
# [main] more or less meaningful JNI calls
################################################################################
[main] time used in function flipBytesByteCopyPointerJNI(): 18508ns
[main] time used in function flipBytesByteShiftPointerJNI(): 17739ns

################################################################################
# [main] more or less meaningful Java calls
################################################################################
[main] time used in function flipBytesByteCopyJava(): 116076ns

from this ill extract the time values and do some graphical presentation.
"""


#standard library imports
import pylab
import sys
import re




class LogFileParser(object):
    """class to parse the log file
    """

    def __init__(self, log_file_name):
        """LogFileParser constructor
        """

        self.__open_log_file(log_file_name)
        self.__parse_and_filter_log_file()
        self.log_file_descriptor.close()
        self.__plot_function_data()


    def __del__(self):
        """destructor
        """
        self.__env.Destroy()


    def __open_log_file(self, file_name):
        self.log_file_descriptor = open(file_name, "r")


    def __parse_and_filter_log_file(self):
        """parse file, drop unused lines
        """

        #list of tuples store the time data values by function name
        self.function_name_value_tuple_list = []
        self.function_name_value_tuple_list.append(("emptyCallJNI", []))
        self.function_name_value_tuple_list.append(("emptyCallGetAndReleaseByteArrayElementsJNI", []))
        self.function_name_value_tuple_list.append(("emptyCallGetAndReleasePrimitiveArrayCriticalJNI", []))
        self.function_name_value_tuple_list.append(("flipBytesByteCopyPointerJNI", []))
        self.function_name_value_tuple_list.append(("flipBytesByteShiftPointerJNI", []))
        self.function_name_value_tuple_list.append(("flipBytesByteCopyJava", []))

        empty_line_regex = re.compile('^\s*$')
        comment_line_regex = re.compile('\s*#')
        byte_array_size_regex = re.compile('^.*SIZE.*$')

        self.__byte_array_size = -1

        for line in self.log_file_descriptor:

            #get byte array size
            if byte_array_size_regex.match(line):
                line_fragments = line.strip().split(':')

                if len(line_fragments) != 2:
                    print '[!] failed to parse!'
                    sys.exit(-4)

                self.__byte_array_size = int(line_fragments[1].strip())
                continue

            #skip empty and comment lines
            if empty_line_regex.match(line) or comment_line_regex.match(line):
                continue

            line = line.strip()

            #get time values by function names from raw input lines
            for function_name in self.function_name_value_tuple_list:
                if not function_name[0] in line:
                    continue
                else:
                    line_fragments = line.split(':')

                    if len(line_fragments) != 2:
                        print '[!] failed to parse!'
                        sys.exit(-4)

                    time_value = line_fragments[1].strip('ns').strip()
                    function_name[1].append(int(time_value))
                    break


    def __plot_function_data(self):
        """plot the crap!
           more information here:
           http://matplotlib.sourceforge.net/api/pyplot_api.html#matplotlib.pyplot.plot
        """

        element_index = 0
        for element in self.function_name_value_tuple_list:
            pylab.plot(element[1], label = element[0])
            element_index += 1

        pylab.xlabel('test run index')
        pylab.ylabel('used time in function [ns]')
        pylab.title('JNI ByteFlipper Performance Test (size of byte array: %d)' % (self.__byte_array_size))
        pylab.grid(True)
        pylab.legend(loc = 'best')

        #pylab.savefig(self.__default_figure_filename)
        pylab.show()




if __name__ == "__main__":
    """
    """

    #check command line arguments
    if len(sys.argv) != 2:
        print "[!] need a filename as argument, nothing else!"
        sys.exit(-1)


    log_file_name = sys.argv[1]
    my_log_file_parser = LogFileParser(log_file_name)









