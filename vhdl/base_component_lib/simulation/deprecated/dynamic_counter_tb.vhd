--------------------------------------------------------------------------
--------------------------------------------------------------------------
-- $LastChangedDate: 2009-12-28 10:42:29 +0100 (Mon, 28 Dec 2009) $
-- $Rev: 202 $
-- $Author: cass $
-- $URL: https://rom.zhaw.ch/svn/kickstart/my_projects/vhdl/base_component_lib/trunk/simulation/deprecated/dynamic_counter_tb.vhd $
-- $Id: dynamic_counter_tb.vhd 202 2009-12-28 09:42:29Z cass $
--------------------------------------------------------------------------
--------------------------------------------------------------------------

-------------------------------------------------------
--! @file dynamic_counter_tb.vhd
--! @brief dynamic counter testbench
-------------------------------------------------------

library ieee;
  use ieee.std_logic_1164.all;
  use ieee.std_logic_arith.all;

library work;
  use work.base_component_pkg.all;

entity dynamic_counter_tb is
end;

architecture dynamic_counter_tb_behaviour of dynamic_counter_tb is

  constant CLK_P : time := 20 ns; --50 MHz
  constant CLK_HALF_P : time := 10 ns;

  signal clk_sig              : std_logic;
  signal reset_sig            : std_logic; --low active!
  signal reset_value_sig      : natural;
  signal counter_value_sig    : natural;

begin

  dynamic_counter_0 : dynamic_counter generic map(
    CNT_START_VALUE => 0,
    CNT_MAX_VALUE => 5
  )
  port map(
    clk_i => clk_sig,
    reset_n_i => reset_sig,
    reset_value_i => reset_value_sig,
    counter_value_o => counter_value_sig
  );



  clk : process
  begin
    clk_sig <= '1';
    wait for CLK_HALF_P;
    clk_sig <= '0';
    wait for CLK_HALF_P;
  end process clk;

  stimulus : process
  begin

    -- do reset
    reset_sig <= '0';
    wait for 5 * CLK_P;
    reset_sig <= '1';
    wait for 5 * CLK_P;


    wait for 50 us;
    reset_value_sig <= 1; 
    wait for 50 us;
    
    -- do reset
    reset_sig <= '0';
    wait for 5 * CLK_P;
    reset_sig <= '1';
    wait for 5 * CLK_P;
    wait for 50 us;
    reset_value_sig <= 0;
    wait for 50 us;
    
    -- do reset
    reset_sig <= '0';
    wait for 5 * CLK_P;
    reset_sig <= '1';
    wait for 5 * CLK_P;
    wait for 50 us;
    reset_value_sig <= 4;
    wait for 50 us;
    
    -- do reset
    reset_sig <= '0';
    wait for 5 * CLK_P;
    reset_sig <= '1';
    wait for 5 * CLK_P;
    wait for 50 us;
    reset_value_sig <= 7;
    wait for 50 us;
    wait;

  end process stimulus;

end architecture dynamic_counter_tb_behaviour;