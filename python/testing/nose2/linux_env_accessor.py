



import os
import logging


logger = logging.getLogger()


class LinuxEnvAccessor(object):

    mandatoryVariableStrings = ['SW_HASH', 'REVISION_STRING']

    @classmethod
    def get(cls):

        env_variable_dict = {}

        for variable_name in LinuxEnvAccessor.mandatoryVariableStrings:
            logger.debug("read variable %s from environment")
            env_variable_dict[variable_name] = os.environ[variable_name]

        print env_variable_dict

        return env_variable_dict


