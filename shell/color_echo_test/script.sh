#!/bin/bash

source ./color_echo.sh

VAR=42

echogray "hello gray"
echogreen "hello green"

echored "echored call $VAR"
echo "echo std call $VAR"
echoyellow "echoyellow call $VAR"
echoblue "echoblue call $VAR"


