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
# simulation options
#################################################################
quietly set VHDL_SIM_SRC_DIR "../../simulation/"
quietly set SIM_VCOM_OPTIONS {-2002 -explicit}
quietly set VSIM_OPTIONS {-hazards -t 1ns}
quietly set SIMULATION_RUN_TIME 50us
quietly vlib simulation_lib

#################################################################


####################################################################
# show simulation environment (alias show_sim_env)
####################################################################
alias show_sim_env {
echo @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
echo @@
echo @@        simulation.do
echo @@
echo @@   defined simulation envoronments:
echo @@
echo @@   0 || generic_counter_tb
echo @@   1 || generic_pwm_tb
echo @@
echo @@
}
####################################################################

####################################################################
# simulation environment: generic_counter_tb
####################################################################
alias generic_counter_tb {

    core
    
    eval vcom $SIM_VCOM_OPTIONS -work simulation_lib $VHDL_SIM_SRC_DIR/generic_counter_tb.vhd
    eval vsim $VSIM_OPTIONS simulation_lib.generic_counter_tb
    
    do wavefiles/wave_generic_counter.do
    run $SIMULATION_RUN_TIME

}
alias 0 generic_counter_tb
####################################################################

####################################################################
# simulation environment: generic_pwm_tb
####################################################################
alias generic_pwm_tb {

    core
    
    eval vcom $SIM_VCOM_OPTIONS -work simulation_lib $VHDL_SIM_SRC_DIR/generic_pwm_tb.vhd
    eval vsim $VSIM_OPTIONS simulation_lib.generic_pwm_tb
    
    do wavefiles/wave_generic_pwm.do
    run $SIMULATION_RUN_TIME

}
alias 1 generic_pwm_tb
####################################################################

