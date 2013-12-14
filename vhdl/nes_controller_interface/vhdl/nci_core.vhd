--------------------------------------------------------------------------
--------------------------------------------------------------------------
-- $LastChangedDate: 2009-03-24 19:43:25 +0100 (Tue, 24 Mar 2009) $
-- $Rev: 116 $
-- $Author: cass $
-- $URL: https://rom.zhaw.ch/svn/kickstart/my_projects/vhdl/nes_controller_interface/trunk/vhdl/nci_core.vhd $
-- $Id: nci_core.vhd 116 2009-03-24 18:43:25Z cass $
--------------------------------------------------------------------------
--------------------------------------------------------------------------

-------------------------------------------------------
--! @file nci_core.vhd
--! @brief nintendo controller interface core
-------------------------------------------------------

--! Use standard library
library ieee;
--! Use logic elements
    use ieee.std_logic_1164.all;

library work;
    use work.nci_pkg.all;

--! nci core entity brief description
--! note: change HALF_FEEL_CLK_COUNT_VALUE to adjust the controller data query, check input clock (50mhz)

--! Detailed description of this
--! mux design element
entity nci_core is
    port (
        -- std signals
        reset_n_i                 : in std_logic;
        clk50m_i                  : in std_logic;

        -- signals from the controller
        nci_in_controller_i       : in type_nci_in_controller;

        -- user data signals
        nci_user_o                : out type_nci_out_user;
        feel_clk_o                : out std_logic;

        -- signals to the nes controller
        nci_out_controller_o      : out type_nci_out_controller
    );
end entity nci_core;

--! @brief nintendo controller interface core
--! @details component instantiation and signal assignments
architecture nci_core_behavior of nci_core is

  ------------------------------------------------------------------------
  -- signal definition
  ------------------------------------------------------------------------
  signal nci_out_user_sig : type_nci_out_user;
  signal controller_clk_sig : std_logic;

begin

  ------------------------------------------------------------------------
  -- component instances
  ------------------------------------------------------------------------

  latch_clock_driver_0 : latch_clock_driver generic map(
      LATCHPULSE_START_VALUE      => 0, --SUBTYPE natural IS integer RANGE 0 TO 2_147_483_647;
      LATCHPULSE_STOP_VALUE       => 599,
      HALF_FEEL_CLK_COUNT_VALUE   => 600000/2,  -- preiod: 12ms -> 83.33hz @ 50mhz input clock
      NR_OF_CONTROLLER_CLK_PULSE  => 8
    )
    port map(
      clk50m_i                    => clk50m_i,
      reset_n_i                   => reset_n_i,
      latch_pulse_o               => nci_out_controller_o.latch,
      feel_clk_o                  => feel_clk_o,
      controller_clk_o            => controller_clk_sig
  );

  data_demux_0 : data_demux port map(
      reset_n_i                   => reset_n_i,
      controller_clk_i            => controller_clk_sig,
      nci_in_controller_i         => nci_in_controller_i,
      nci_out_user_o              => nci_out_user_sig
  );

  nci_user_o                      <= nci_out_user_sig;
  nci_out_controller_o.ctrl_clk   <= controller_clk_sig;

end architecture nci_core_behavior;
