--------------------------------------------------------------------------
--------------------------------------------------------------------------
-- $LastChangedDate: 2009-12-28 10:42:29 +0100 (Mon, 28 Dec 2009) $
-- $Rev: 202 $
-- $Author: cass $
-- $URL: https://rom.zhaw.ch/svn/kickstart/my_projects/vhdl/base_component_lib/trunk/vhdl/deprecated/generic_clock_generator.vhd $
-- $Id: generic_clock_generator.vhd 202 2009-12-28 09:42:29Z cass $
--------------------------------------------------------------------------
--------------------------------------------------------------------------

-------------------------------------------------------
--! @file generic_clock_generator.vhd
--! @brief generic clock generator
-------------------------------------------------------

library ieee;
  use ieee.std_logic_1164.all;
  use ieee.numeric_std.all;

library work;
  use work.base_component_pkg.all;

entity generic_clock_generator is
  generic(
    CLK_CNT_VALUE : natural := 0 --SUBTYPE natural IS integer RANGE 0 TO 2_147_483_647;
  );
  port (
    clk_i            : in std_logic;
    reset_n_i        : in std_logic;
    clk_o            : out std_logic
  );
end generic_clock_generator;

-- reset nur auf den logikprozess
architecture generic_clock_generator_behaviour of generic_clock_generator is

  signal generic_counter_out              : type_generic_counter_out;
  signal clk_clkgenerator_signal_next     : std_logic;
  signal clk_clkgenerator_signal          : std_logic;

begin

  -- components
  counter_0 : generic_counter generic map(
    CNT_START_VALUE => 0,
    CNT_MAX_VALUE => CLK_CNT_VALUE
  )
  port map(
    clk_i => clk_i,
    reset_n => reset_n_i,
    counter_out => generic_counter_out
  );

  clocked : process(reset_n_i, clk_i)
  begin
    if reset_n_i = '0' then
      clk_clkgenerator_signal <= '0';
    elsif rising_edge(clk_i) then
      if generic_counter_out.overflow_pulse = '1' then
        clk_clkgenerator_signal <= clk_clkgenerator_signal_next;
      else
        clk_clkgenerator_signal <= clk_clkgenerator_signal;
      end if;
    else
      clk_clkgenerator_signal <= clk_clkgenerator_signal;
    end if;
  end process clocked;

  clk_clkgenerator_signal_next <= not clk_clkgenerator_signal;

  clk_o <= clk_clkgenerator_signal;


end architecture generic_clock_generator_behaviour;
