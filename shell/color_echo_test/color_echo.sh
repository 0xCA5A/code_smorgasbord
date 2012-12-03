

# script generates a list of functions you can call
# in this form: echoCOLOR "hello world $VAR"

# color source
#http://en.wikipedia.org/wiki/Tput

TMP_DIR=/tmp


txtgrey="$(tput setaf 0)"
txtdarkgrey="$(tput bold ; tput setaf 0)"
txtred="$(tput setaf 1)"
txtlightred="$(tput bold ; tput setaf 1)"
txtgreen="$(tput setaf 2)"
txtlightgreen="$(tput bold ; tput setaf 2)"
txtbrown="$(tput setaf 3)"
txtyellow="$(tput bold ; tput setaf 3)"
txtblue="$(tput setaf 4)"
txtlightblue="$(tput bold ; tput setaf 4)"
txtpurple="$(tput setaf 5)"
txtpink="$(tput bold ; tput setaf 5)"
txtcyan="$(tput setaf 6)"
txtlightcyan="$(tput bold ; tput setaf 6)"
txtlightgrey="$(tput setaf 7)"
txtwhite="$(tput bold ; tput setaf 7)"

txtrst="$(tput sgr0)" # reset, no color

bggrey="$(tput setab 0)"
bgred="$(tput setab 1)"
bggreen="$(tput setab 2)"
bgbrown="$(tput setab 3)"
bgblue="$(tput setab 4)"
bgpurple="$(tput setab 5)"
bgcyan="$(tput setab 6)"
bglightgrey="$(tput setab 7)"


for element in $(cat color_echo.sh | grep -E '^txt.*=' | grep -v txtrst | cut -d '=' -f 1 | sed 's/txt//g');
    do echo -e "function echo$element() {\n    echo -e \"\${txt$element}\${1}\${txtrst}\"\n}"; 
done > $TMP_DIR/$$_echo_functions.sh

source $TMP_DIR/$$_echo_functions.sh
# cat $TMP_DIR/$$_echo_functions.sh | grep 'function echo' | cut -d  ' ' -f 2 | tr -d '()'
# cat $TMP_DIR/$$_echo_functions.sh
rm -f $TMP_DIR/$$_echo_functions.sh

