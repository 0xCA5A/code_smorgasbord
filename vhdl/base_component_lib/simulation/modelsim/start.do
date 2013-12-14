################################################################################
#
#     tcl script to build vhdl projects using modelsim / questa
#
# $LastChangedDate:$
# $Rev:$
# $Author:$
# $URL:$
# $Id:$
################################################################################


#################################################################
# NOTE
#################################################################
#
# 
#
#################################################################


#################################################################
# modelsim.ini
#################################################################
#
# NOTE: this is done because of the xilinx simulation libs...
#
if {![file exists modelsim.ini]} {
  echo "NOTE: local modelsim.ini does not exist! copy modelsim.ini from questa dir..."
  exec cp /opt/questasim/modelsim.ini modelsim.ini
  exec chmod +w modelsim.ini
  echo "done!\n\n"
  echo "*********************************************************************"
  echo "* NOTE: restart modelsim to use the local modlsim.ini file."
  echo "*********************************************************************"

  exit

}
#################################################################


#################################################################
# global stuff (alias globals)
#################################################################
alias globals {

    #library setup
    quietly vlib base_component_lib

    # script settings
    quietly set VHDL_COMPONENT_SRC_DIR "../../vhdl/"
    quietly set VHDL_COMPONENT_COMPILE_FILE  "component_files.f"
    quietly set CORE_VCOM_OPTIONS {-93 -O0 -explicit -time -check_synthesis -lint -pedanticerrors -quiet -rangecheck -source}
    #quietly set CORE_VCOM_OPTIONS {-93 -O0 -explicit -time -check_synthesis -lint -quiet -rangecheck -source}
    #
    # -93                 Enable support for VHDL 1076-1993
    # -O0                 Disable optimizations
    # -explicit           Resolve resolution conflicts in favor of explicit functions
    # -time               Print the compilation wall clock time
    # -check_synthesis    Check for compliance to some synthesis rules
    # -lint               Perform lint-style checks
    # -pedanticerrors     Enforce strict language checks
    # -quiet              Disable 'Loading' messages
    # -rangecheck        Enable run-time range checks
    # -source            Print the source line with error messages

}
#################################################################


####################################################################
# build vhdl components (alias core)
####################################################################
alias core {
    globals
    eval ls $VHDL_COMPONENT_SRC_DIR/*.vhd > $VHDL_COMPONENT_COMPILE_FILE
    eval vcom $CORE_VCOM_OPTIONS -f $VHDL_COMPONENT_COMPILE_FILE -work base_component_lib
}
####################################################################


#################################################################
# clean project  (alias clean)
#################################################################
alias clean {
  quit -sim # Unloads the current design in the simulator without exiting QuestaSim. All files opened by the simulation will be closed including the WLF file (vsim.wlf)

  echo "clean project..."
  vdel -all
  exec rm -f $VHDL_COMPONENT_COMPILE_FILE
  echo "done!"
}
#################################################################


####################################################################
# simulation envoronment (alias simulation)
####################################################################
alias simulation {
  show_sim_env
}
####################################################################


####################################################################
# show help menu (alias h)
####################################################################
alias h "
echo @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
echo @@
echo @@        start.do
echo @@
echo @@   defined aliases:
echo @@
echo @@   global  -- global settings
echo @@          set compiler options and file path etc.
echo @@
echo @@   core  -- compile components
echo @@          re-compile components
echo @@
echo @@   simulation  -- show testbenches
echo @@          re-compile testbenches after selection and run simulation after that...
echo @@
echo @@
echo @@   h  -- print this message
echo @@"
####################################################################


####################################################################
# include simulation environment
####################################################################

do simulation.do

####################################################################


####################################################################
# start!
####################################################################

# show menu
h

####################################################################


