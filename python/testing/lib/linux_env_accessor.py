import os
import logging

logger = logging.getLogger()
logger = logging.getLogger()
logger.setLevel(logging.INFO)


class LinuxEnvAccessor(object):

    mandatoryVariableStrings = ['SW_HASH', 'REVISION_STRING', 'MAGIC_NUMBER']

    @classmethod
    def get(cls):

        env_variable_dict = {}

        for variable_name in LinuxEnvAccessor.mandatoryVariableStrings:
            logger.debug("read variable %s from environment")
            env_variable_dict[variable_name] = os.environ[variable_name]

        return env_variable_dict


