--------------------------------------------------------------------------
--------------------------------------------------------------------------
-- $LastChangedDate: 2009-12-28 14:05:14 +0100 (Mon, 28 Dec 2009) $
-- $Rev: 205 $
-- $Author: cass $
-- $URL: https://rom.zhaw.ch/svn/kickstart/my_projects/vhdl/base_component_lib/trunk/simulation/generic_counter_tb.vhd $
-- $Id: generic_counter_tb.vhd 205 2009-12-28 13:05:14Z cass $
--------------------------------------------------------------------------
--------------------------------------------------------------------------

-------------------------------------------------------
--! @file generic_counter_tb.vhd
--! @brief vhdl testbench for generic counter
------------------------------------------------------


library ieee;
    use ieee.std_logic_1164.all;

library base_component_lib;
    use base_component_lib.base_component_pkg.all;


entity generic_counter_tb is
end;

architecture generic_counter_tb_behaviour of generic_counter_tb is

    constant CLK_P                                  : time := 20 ns; --50 MHz 
    constant CLK_HALF_P                             : time := 10 ns;

    constant GENERIC_COUNTER_MIN_CNT_VALUE_TB_CONST : natural := 10;
    constant GENERIC_COUNTER_MAX_CNT_VALUE_TB_CONST : natural := 17;
    constant GENERIC_COUNTER_MAX_BITS_TB_CONST      : natural := 5;

    signal clk_sig                                  : std_logic;
    signal reset_n_sig                              : std_logic; --low active!
    signal generic_counter_value_sig                : std_logic_vector(GENERIC_COUNTER_MAX_BITS_TB_CONST - 1 downto 0);


begin

    generic_counter_0 : entity base_component_lib.generic_counter
    generic map(
        GENERIC_COUNTER_MIN_CNT_VALUE => GENERIC_COUNTER_MIN_CNT_VALUE_TB_CONST,
        GENERIC_COUNTER_MAX_CNT_VALUE => GENERIC_COUNTER_MAX_CNT_VALUE_TB_CONST,
        GENERIC_COUNTER_MAX_BITS => GENERIC_COUNTER_MAX_BITS_TB_CONST 
    )
    port map(
        clk_i => clk_sig,
        reset_n_i => reset_n_sig,
        generic_counter_value_o => generic_counter_value_sig
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
        reset_n_sig <= '0';
        wait for 5 * CLK_P;
        reset_n_sig <= '1';
        wait for 5 * CLK_P;

        wait for 23 * CLK_P;

        -- do reset again
        reset_n_sig <= '0';
        wait for 5 * CLK_P;
        reset_n_sig <= '1';
        wait for 5 * CLK_P;

        wait;

    end process stimulus;

end architecture generic_counter_tb_behaviour;
