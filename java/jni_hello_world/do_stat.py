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
#import collections



class LogFileParser(object):
    """class to parse the log file
    """

    def __init__(self, log_file_name):
        """LogFileParser constructor
        """

        self.__byte_array_size = 0;
        self.__function_name_value_tuple_list = []
        self.__log_file_descriptor = None

        self.log_file_name = log_file_name
        self.__open_log_file(self.log_file_name)
        self.__parse_and_filter_log_file()
        self.__log_file_descriptor.close()


    def __del__(self):
        """destructor
        """


    def __open_log_file(self, file_name):
        """open log file
        """

        self.__log_file_descriptor = open(file_name, "r")


    def __parse_and_filter_log_file(self):
        """parse file, drop unused lines
        """

        #list of tuples store the time data values by function name
        self.__function_name_value_tuple_list.append(("emptyCallJNI", []))
        self.__function_name_value_tuple_list.append(("emptyCallGetAndReleaseByteArrayElementsJNI", []))
        self.__function_name_value_tuple_list.append(("emptyCallGetAndReleasePrimitiveArrayCriticalJNI", []))
        self.__function_name_value_tuple_list.append(("flipBytesByteCopyPointerJNI", []))
        self.__function_name_value_tuple_list.append(("flipBytesByteShiftPointerJNI", []))
        self.__function_name_value_tuple_list.append(("flipBytesByteXORPointerJNI", []))
        self.__function_name_value_tuple_list.append(("flipBytesByteCopyJava", []))

        empty_line_regex = re.compile('^\s*$')
        comment_line_regex = re.compile('\s*#')
        byte_array_size_regex = re.compile('^.*SIZE.*$')

        self.__byte_array_size = -1

        for line in self.__log_file_descriptor:

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
            for function_name in self.__function_name_value_tuple_list:
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


    def print_stat_from_function_data(self):
        """print stats of the collected data
        """
        print '[main] print statistics'

        for element in self.__function_name_value_tuple_list:
            print 'function: %s' % element[0]
            print ' * nr of values: %d' % len(element[1])
            print ' * avg: %dns' % (int(sum(element[1])) / len(element[1]))
            print ' * max: %dns' % max(element[1])
            print ' * min: %dns' % min(element[1])


    def plot_function_data_simple(self):
        """plot the crap in a simple way!
           more information here:
           http://matplotlib.sourceforge.net/api/pyplot_api.html#matplotlib.pyplot.plot
        """

        print '[main] plot data'

        function_name_to_plot_regex = re.compile('^.*(flip|Critical).*$')

        #size in inches, come on...
        pylab.figure(figsize = (32, 18))

        #plot data
        for element in self.__function_name_value_tuple_list:
            if function_name_to_plot_regex.match(element[0]):
                pylab.plot(element[1], label = element[0])

        #decorate plot
        pylab.xlabel('test run index')
        pylab.ylabel('used time in function [ns]')
        pylab.title('JNI ByteFlipper Performance Test (size of byte array: %d)' % (self.__byte_array_size))
        pylab.grid(True)
        pylab.legend(loc = 'best')

        plot_file_name = self.log_file_name.strip('.log') + '_simple.png'
        print '[main] write simple plot to image (%s)' % plot_file_name
        pylab.savefig(plot_file_name)
        #pylab.show()


    def plot_function_data_scientific(self):
        """plot the crap in a more scientific way!
           more information here:
           http://matplotlib.sourceforge.net/api/pyplot_api.html#matplotlib.pyplot.plot
        """

        print '[main] plot data'

        function_name_to_plot_regex = re.compile('^.*(flip|Critical).*$')

        #size in inches, come on...
        pylab.figure(figsize = (32, 18))

        #plot data
        for element in self.__function_name_value_tuple_list:
            if function_name_to_plot_regex.match(element[0]):
                #for time_value in element[1]:
                    #counter_dict = collections.Counter(element[1])

                pylab.hist(element[1], label = element[0], histtype = 'bar')
                #pylab.plot(counter_dict.keys(), counter_dict.values(), 'O', label = element[0])

        #decorate plot
        pylab.xlabel('used time in function [ns]')
        pylab.ylabel('value count')
        pylab.title('JNI ByteFlipper Performance Test (size of byte array: %d)' % (self.__byte_array_size))
        pylab.grid(True)
        pylab.legend(loc = 'best')

        plot_file_name = self.log_file_name.strip('.log') + '_scientific.png'
        print '[main] write simple plot to image (%s)' % plot_file_name
        pylab.savefig(plot_file_name)
        #pylab.show()



if __name__ == "__main__":

    #check command line arguments
    if len(sys.argv) != 2:
        print "[!] need a filename as argument, nothing else!"
        sys.exit(-1)


    LOG_FILE_NAME = sys.argv[1]
    LOG_FILE_PARSER = LogFileParser(LOG_FILE_NAME)
    LOG_FILE_PARSER.print_stat_from_function_data()
    LOG_FILE_PARSER.plot_function_data_simple()
    LOG_FILE_PARSER.plot_function_data_scientific()








