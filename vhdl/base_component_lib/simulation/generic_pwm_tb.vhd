--------------------------------------------------------------------------
--------------------------------------------------------------------------
-- $LastChangedDate: 2009-12-28 14:53:09 +0100 (Mon, 28 Dec 2009) $
-- $Rev: 207 $
-- $Author: cass $
-- $URL: https://rom.zhaw.ch/svn/kickstart/my_projects/vhdl/base_component_lib/trunk/simulation/generic_pwm_tb.vhd $
-- $Id: generic_pwm_tb.vhd 207 2009-12-28 13:53:09Z cass $
--------------------------------------------------------------------------
--------------------------------------------------------------------------

-------------------------------------------------------
--! @file dynamic_pwm_tb.vhd
--! @brief dynamic pwm testbench
-------------------------------------------------------

library ieee;
    use ieee.std_logic_1164.all;
    use ieee.numeric_std.all;

library base_component_lib;
    use base_component_lib.base_component_pkg.all;

entity generic_pwm_tb is
end;

architecture generic_pwm_tb_behaviour of generic_pwm_tb is

    constant CLK_P              : time := 20 ns; --50 MHz
    constant CLK_HALF_P         : time := 10 ns;
    constant GENERIC_PWM_MAX_CNT_BITS_TB_CONST : natural := 5;

    signal clk_sig              : std_logic;
    signal reset_n_sig          : std_logic; --low active!
    signal pwm_duty_sig         : std_logic_vector(GENERIC_PWM_MAX_CNT_BITS_TB_CONST - 1 downto 0);
    signal pwm_sig              : std_logic;

begin

    generic_pwm_0 : entity base_component_lib.generic_pwm
    generic map(
        GENERIC_PWM_MAX_PERIOD_VALUE            => 19, -- start at 0!
        GENERIC_PWM_MAX_CNT_BITS                => GENERIC_PWM_MAX_CNT_BITS_TB_CONST,
        GENERIC_PWM_DUTY_SIGNAL_START_LEVEL     => '1'
    )
    port map(
        clk_i                   => clk_sig,
        reset_n_i               => reset_n_sig,
        pwm_duty_value_i        => pwm_duty_sig,
        pwm_o                   => pwm_sig
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


        -- signal assignment
        pwm_duty_sig <= (others => '0');     

        -- do reset
        reset_n_sig <= '0';
        wait for 5 * CLK_P;
        reset_n_sig <= '1';

        wait for 20 * CLK_P;
        wait for 20 * CLK_P;
        wait for 20 * CLK_P;        

        pwm_duty_sig <= std_logic_vector(to_unsigned(10, GENERIC_PWM_MAX_CNT_BITS_TB_CONST));
        wait for 20 * CLK_P;
        wait for 20 * CLK_P;
        wait for 20 * CLK_P;

        pwm_duty_sig <= std_logic_vector(to_unsigned(19, GENERIC_PWM_MAX_CNT_BITS_TB_CONST));  
        wait for 20 * CLK_P;
        wait for 20 * CLK_P;
        wait for 20 * CLK_P;

        pwm_duty_sig <= std_logic_vector(to_unsigned(7, GENERIC_PWM_MAX_CNT_BITS_TB_CONST));  
        wait for 20 * CLK_P;
        wait for 20 * CLK_P;
        wait for 20 * CLK_P;

        wait;

    end process stimulus;

end architecture generic_pwm_tb_behaviour;
