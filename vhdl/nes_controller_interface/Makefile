################################################################################
#
#          makefile to build vhdl projects using modelsim / questa
#
# $LastChangedDate: 2008-07-24 08:34:39 +0200 (Thu, 24 Jul 2008) $
# $Rev: 547 $
# $Author: casa $
# $URL: https://casa@thepacki.homelinux.org/svn/repos/neap/tools/Makefile_template $
# $Id: Makefile_template 547 2008-07-24 06:34:39Z casa $
################################################################################

################################################################################
# features
# - help function
################################################################################

################################################################################
# todo
# - check if lib exists before vlib call
# - add something for doxygen
################################################################################

################################################################################
# variable
################################################################################
VHDL_SOURCE_DIR=./vhdl/
SIM_DIR=./simulation/
#SYNTH_DIR=./synthesis/
DOC_DIR=./doc/
HELP = @grep -B1 '^[a-zA-Z][^:]*:' Makefile |\
      awk 'function p(h,t){printf"%-12s=%s\n",h,t};\
        /\#+/{t=$$0};\
        /:/{gsub(":.*","");h=$$0};\
        /--/{p(h,t);t=h="";};\
        END{p(h,t)}' |\
      sed -n 's/=.*\#+/:/gp'

################################################################################
# make
################################################################################


# add new components here
################################################################################
#+ build all the components with the testbenches
all: nci nci_core_tb

# #+ build the components
# core: counter

# #+ build testbenaches
# testbenches: counter_tb
################################################################################

# global stuff
################################################################################
#+ build the environment (basic setup)
env:
	@echo
	@echo ---------------------------------------------------------------------
	@echo do target $@
	@echo ---------------------------------------------------------------------
	if test -d work; \
		then echo lib exists!; \
		else vlib work; \
	fi
	@echo ---------------------------------------------------------------------
	@echo $@ done!
	@echo ---------------------------------------------------------------------

#+ build the interface types for the nintendo controller interface core
nci_pkg: env ${VHDL_SOURCE_DIR}nci_pkg.vhd
	@echo
	@echo ---------------------------------------------------------------------
	@echo do target $@
	@echo ---------------------------------------------------------------------
	if test -d nci_pkg; \
		then echo lib exists!; \
		else vlib nci_pkg; \
	fi
	#vmap nci_interface work; cat modelsim.ini | grep nci;
	vmap nci_pkg work;
	vcom -2002 -explicit -work nci_pkg $(VHDL_SOURCE_DIR)nci_pkg.vhd
	@echo ---------------------------------------------------------------------
	@echo $@ done!
	@echo ---------------------------------------------------------------------

#+ prints this list
help:
	${HELP}
	
#+ clean project
clean:
	rm -rf work
	rm -rf nci_pkg
	if test -e modelsim.ini; \
		then rm -f modelsim.ini; \
	fi
#	rm *~
################################################################################

# # clock divider
# ################################################################################
# #+ build the clock divider
# clk_div: nci_pkg ${VHDL_SOURCE_DIR}clk_div.vhd
# 	@echo
# 	@echo ---------------------------------------------------------------------
# 	@echo do target $@
# 	@echo ---------------------------------------------------------------------
# 	if test -d nci_pkg; \
# 		then echo lib exists!; \
# 		else vlib nci_pkg; \
# 	fi
# 	#vmap nci_pkg work; cat modelsim.ini | grep base_;
# 	vmap nci_pkg work;
# 	vcom -2002 -explicit -work nci_pkg $(VHDL_SOURCE_DIR)clk_div.vhd
# 	@echo ---------------------------------------------------------------------
# 	@echo $@ done!
# 	@echo ---------------------------------------------------------------------
# ################################################################################


# latch clock driver
################################################################################
#+ build the latch driver
latch_clock_driver: nci_pkg ${VHDL_SOURCE_DIR}latch_clock_driver.vhd
	@echo
	@echo ---------------------------------------------------------------------
	@echo do target $@
	@echo ---------------------------------------------------------------------
	if test -d nci_pkg; \
		then echo lib exists!; \
		else vlib nci_pkg; \
	fi
	#vmap nci_pkg work; cat modelsim.ini | grep base_;
	vmap nci_pkg work;
	vcom -2002 -explicit -work nci_pkg $(VHDL_SOURCE_DIR)latch_clock_driver.vhd
	@echo ---------------------------------------------------------------------
	@echo $@ done!
	@echo ---------------------------------------------------------------------
################################################################################

# data demultiplexer unit
################################################################################
#+ build the data demultiplexer unit
data_demux: nci_pkg ${VHDL_SOURCE_DIR}data_demux.vhd
	@echo
	@echo ---------------------------------------------------------------------
	@echo do target $@
	@echo ---------------------------------------------------------------------
	if test -d nci_pkg; \
		then echo lib exists!; \
		else vlib nci_pkg; \
	fi
	#vmap nci_pkg work; cat modelsim.ini | grep base_;
	vmap nci_pkg work;
	vcom -2002 -explicit -work nci_pkg $(VHDL_SOURCE_DIR)data_demux.vhd
	@echo ---------------------------------------------------------------------
	@echo $@ done!
	@echo ---------------------------------------------------------------------
################################################################################

# # data handler unit
# ################################################################################
# #+ build the data handler unit
# data_handler: nci_pkg ${VHDL_SOURCE_DIR}data_handler.vhd
# 	@echo
# 	@echo ---------------------------------------------------------------------
# 	@echo do target $@
# 	@echo ---------------------------------------------------------------------
# 	if test -d nci_pkg; \
# 		then echo lib exists!; \
# 		else vlib nci_pkg; \
# 	fi
# 	#vmap nci_pkg work; cat modelsim.ini | grep base_;
# 	vmap nci_pkg work;
# 	vcom -2002 -explicit -work nci_pkg $(VHDL_SOURCE_DIR)data_handler.vhd
# 	@echo ---------------------------------------------------------------------
# 	@echo $@ done!
# 	@echo ---------------------------------------------------------------------
# ################################################################################


# nci core
################################################################################
#+ build the nci core
nci: nci_pkg data_demux latch_clock_driver ${VHDL_SOURCE_DIR}nci_core.vhd
	@echo
	@echo ---------------------------------------------------------------------
	@echo do target $@
	@echo ---------------------------------------------------------------------
	if test -d nci_pkg; \
		then echo lib exists!; \
		else vlib nci_pkg; \
	fi
	#vmap nci_pkg work; cat modelsim.ini | grep base_;
	vmap nci_pkg work;
	vcom -2002 -explicit -work nci_pkg $(VHDL_SOURCE_DIR)nci_core.vhd
	@echo ---------------------------------------------------------------------
	@echo $@ done!
	@echo ---------------------------------------------------------------------
################################################################################

# testbenches
################################################################################
#+ build the nci core counter test bench
nci_core_tb: nci ${SIM_DIR}nci_core_tb.vhd
	@echo
	@echo ---------------------------------------------------------------------
	@echo do target $@
	@echo ---------------------------------------------------------------------
	vcom -2002 -explicit -work work $(SIM_DIR)nci_core_tb.vhd
	@echo ---------------------------------------------------------------------
	@echo $@ done!
	@echo ---------------------------------------------------------------------
################################################################################

# simulation
################################################################################
#+ run the simulator for generic_counter testbench
nci_core_sim: nci_core_tb
	vsim -t 1ns -l simlog.txt work.nci_core_tb
	# -t : time resolution limit
################################################################################









# old stuff
################################################################################

# 
# #+ build globals package
# globals: ${VHDL_SOURCE_DIR}globals.vhd
# 	vlib work
# 	vlib globals
# 	vcom -2002 -explicit -work globals ${VHDL_SOURCE_DIR}globals.vhd
# 	vmap globals work
# 
# #+ build the interface package
# if : globals ${VHDL_SOURCE_DIR}my_interface_lib.vhd
# 	vlib my_interface_lib
# 	vcom -2002 -explicit -work my_interface_lib ${VHDL_SOURCE_DIR}my_interface_lib.vhd
# 	vmap my_interface_lib work
# 
# #+ build the sram_stimulus
# stimulus: core sram_stimulus.vhd
# 	vcom -2002 -explicit -work work sram_stimulus.vhd
# 
# #+ build the sram_arbiter_50_tb testbench
# tb: core sram_arbiter_50_tb.vhd
# 	vcom -2002 -explicit -work work sram_arbiter_50_tb.vhd
# 


 
