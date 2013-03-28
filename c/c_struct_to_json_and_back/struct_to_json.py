#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os
import sys

import subprocess
import re



class CStructure(object):

    def __init__(self, c_structure_raw_data):
        self.type_name = ""
        print c_structure_raw_data

    def set_type_name(self, type_name):
        self.type_name = type_name


    def get_as_json(self):

        json_data = ""
        return json_data




class TIAERHeaderFileParser(object):
    """todo:

    check input from here:
        * http://stackoverflow.com/questions/241327/python-snippet-to-remove-c-and-c-comments
    """

    def __c_comment_remover(self, lines):

        #convert lines to text
        text = ""
        for line in lines:
            text = text + str(line)

        def replacer(match):
            s = match.group(0)
            if s.startswith('/'):
                return ""
            else:
                return s

        pattern = re.compile(
            r'//.*?$|/\*.*?\*/|\'(?:\\.|[^\\\'])*\'|"(?:\\.|[^\\"])*"',
            re.DOTALL | re.MULTILINE
        )
        filtered_text = re.sub(pattern, replacer, text)

        return filtered_text.split('\n')


    def __get_c_structures(self, lines):

        #convert lines to text
        text = ""
        for line in lines:
            text = text + str(line)

        pattern = re.compile(
            r'struct.+{.*}',
            re.DOTALL | re.MULTILINE
        )
        
        structure_raw_list = re.findall(pattern, text)

        print type(structure_raw_list)
        print len(structure_raw_list)
        
        structures = []
        for structure_raw in structure_raw_list:
            structures.append(CStructure(structure_raw))
            #print structure_raw


        return structures
        
        #return filtered_text.split('\n')

    def __init__(self):
        print '[i] hello from TIAERHeaderFileParser constructor'
        #self._config = config
        #self._temp_reader = TempReader()
        #self._load_reader = LoadReader()
        #self._view = View(self)

    def __preprocess_input_file(self, input_file_name):
        preprocessed_input_data = subprocess.Popen("cpp -fpreprocessed " + input_file_name, stdout=subprocess.PIPE, shell=True).stdout.read()

        #return raw data as a list, line by line
        return preprocessed_input_data.split('\n')


    def __read_lines_from_file(self, filename):
        with open(filename) as file_:
            lines = []
            for line in file_:
                lines.append(line)
        return lines


    def __filter_lines(self, lines):
        filtered_lines = self.__c_comment_remover(lines)

        #filter empty lines
        emptylineless_filtered_lines = []
        for line in filtered_lines:
            if not line.strip():
                continue
            else:
                emptylineless_filtered_lines.append(line)

        return emptylineless_filtered_lines


    def __get_json_from_struct_data(self, struct_name):
        print "helo"


    def __print_structures(self, filtered_lines):
        for line in filtered_lines:
            print line



    def parse(self, file_name):

        #struct_list = []
        #struct_list.append("aerControl_s")
        #struct_list.append("aerConfig_s")

        #preprocessed_input_lines = self.__preprocess_input_file(filename)

        #print type(preprocessed_input_data)

        #for line in preprocessed_input_data:
            #print line
        
        raw_lines = self.__read_lines_from_file(file_name)
        filtered_lines = self.__filter_lines(raw_lines)
        
        #filtered_lines = self.__filter_lines(preprocessed_input_lines)
        #for line in filtered_lines:
            #print line


        c_structures = self.__get_c_structures(filtered_lines)


        return c_structures
        
        #for c_structure in c_structures:
            #print c_structure
            

            
        #struct_name = "aerConfig_s"
        #self.__get_json_from_struct_data(struct_name)


if __name__ == "__main__":

    #check if root or not...
    #if not os.geteuid() == 0:
        #sys.exit('script must be run as root...')

    file_name = "/home/sam/projects/github/audiocore-dsp/jack/clients/lib_hard/ti/mas/aer/aer.h"

    ti_aer_header_file_parser = TIAERHeaderFileParser()
    c_structures = ti_aer_header_file_parser.parse(file_name)

    
    

