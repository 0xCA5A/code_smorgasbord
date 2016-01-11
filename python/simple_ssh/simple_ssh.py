#!/usr/bin/env python2
from paramiko import SSHClient, MissingHostKeyPolicy, AuthenticationException
import sys
import logging
import re
import socket
import argparse


logging.basicConfig(level=logging.INFO)
log = logging.getLogger(__name__)

if __name__ == "__main__":
    logging.getLogger("paramiko").setLevel(logging.ERROR)

    # defaults
    port = 22
    user = "root"
    password = "12345"
    remote = "192.168.1.2"
    command = "ls /tmp"

    # parse arguments
    parser = argparse.ArgumentParser()
    parser.add_argument("-c", "--command",
                        help="command to run on remote host (default: {})".format(command))
    parser.add_argument("-r", "--remote",
                        help="remote host (default: {})".format(remote))
    args = parser.parse_args()

    if args.command:
        command = args.command

    if args.remote:
        remote = args.remote.strip()

    # try to open a session
    ssh_client = SSHClient()
    ssh_client.set_missing_host_key_policy(MissingHostKeyPolicy())
    try:
        ssh_client.connect(remote, port, user, password)
    except socket.error:
        log.error("no SSH connection possible to '{}:{}'!".format(remote, port))
        ssh_client.close()
        sys.exit(1)
    except AuthenticationException:
        log.error("wrong username or password!")
        ssh_client.close()
        sys.exit(1)

    # check if we are connected to the target device
    if not ssh_client.get_transport():
        log.error("not connected to host {}".format(remote))
        ssh_client.close()
        sys.exit(1)

    # call on remote host
    log.info("call command '{}' on host {}:{}".format(command, remote, port))
    _, stdout, stderr = ssh_client.exec_command(command + "; echo SSH_EXEC_TEST: $?")

    # get stdout / command return code
    ret_value = None
    stdout_output = ""
    stdout_lines = stdout.readlines()
    for line in stdout_lines:
        match = re.match('^SSH_EXEC_TEST: (\d+)', line)
        if match:
            ret_value = int(match.group(1))
        else:
            stdout_output += line
    stdout_output = stdout_output.strip('\r\n')

    stderr_output = ""
    stderr_lines = stderr.readlines()
    for line in stderr_lines:
        stderr_output += line
    stderr_output = stderr_output.strip('\r\n')

    log.info("return value: {}".format(ret_value))
    if stderr_output:
        log.info("stderr: {}".format(stderr_output))
    if stdout_output:
        log.info("stdout_output: {}".format(stdout_output))

    ssh_client.close()
