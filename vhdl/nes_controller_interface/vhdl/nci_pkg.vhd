-------------------------------------------------------
--! @file nci_pkg.vhd
--! @brief component if type definition
------------------------------------------------------


--------------------------------------------------------------------------
-- note
--
--------------------------------------------------------------------------
-- nes controller interface package
--------------------------------------------------------------------------
-- check http://users.ece.gatech.edu/~hamblen/489X/f04proj/USB_NES/protocol.htm
-- for signal information
-- check http://www.mit.edu/~tarvizo/nes-controller.html
-- for signals and cable colors


library ieee;
  use ieee.std_logic_1164.all;

package nci_pkg is

  ------------------------------------------------------------------------
  -- record / type definition
  ------------------------------------------------------------------------
  type type_nci_out_user is record
    up            : std_logic;
    down          : std_logic;
    left          : std_logic;
    right         : std_logic;
    sel           : std_logic;
    start         : std_logic;
    a             : std_logic;
    b             : std_logic;
  end record;

  type type_nci_in_controller is record
    data_n        : std_logic;
  end record;

  type type_nci_out_controller is record
    ctrl_clk      : std_logic;
    latch         : std_logic;
  end record;

  ------------------------------------------------------------------------
  -- component definition
  ------------------------------------------------------------------------
  component latch_clock_driver is
    generic(
      LATCHPULSE_START_VALUE : natural := 0; --SUBTYPE natural IS integer RANGE 0 TO 2_147_483_647;
      LATCHPULSE_STOP_VALUE : natural := 0;
      HALF_FEEL_CLK_COUNT_VALUE : natural := 0;
      NR_OF_CONTROLLER_CLK_PULSE : natural := 0
    );
    port (
      clk50m_i         : in std_logic;
      reset_n_i        : in std_logic; --low active!
      latch_pulse_o    : out std_logic;
      feel_clk_o       : out std_logic;
      controller_clk_o : out std_logic
    );
  end component latch_clock_driver;

  component data_demux is
    port (
      reset_n_i             : in std_logic; --low active!
      controller_clk_i      : in std_logic;
      nci_in_controller_i   : in type_nci_in_controller;
      nci_out_user_o        : out type_nci_out_user
    );
  end component data_demux;

  component nci_core is
    port (
        reset_n_i             : in std_logic;
        clk50m_i              : in std_logic;
        nci_in_controller_i   : in type_nci_in_controller;

        nci_out_controller_o  : out type_nci_out_controller;
        nci_user_o            : out type_nci_out_user;
        feel_clk_o            : out std_logic
    );
  end component nci_core;

end package nci_pkg;
