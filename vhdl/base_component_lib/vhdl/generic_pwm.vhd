--------------------------------------------------------------------------
--------------------------------------------------------------------------
-- $LastChangedDate: 2009-12-28 14:53:09 +0100 (Mon, 28 Dec 2009) $
-- $Rev: 207 $
-- $Author: cass $
-- $URL: https://rom.zhaw.ch/svn/kickstart/my_projects/vhdl/base_component_lib/trunk/vhdl/generic_pwm.vhd $
-- $Id: generic_pwm.vhd 207 2009-12-28 13:53:09Z cass $
--------------------------------------------------------------------------
--------------------------------------------------------------------------

-------------------------------------------------------
--! @file generic_pwm.vhd
--! @brief generic pwm
-------------------------------------------------------

library ieee;
    use ieee.std_logic_1164.all;

library base_component_lib;
    use base_component_lib.base_component_pkg.all;


entity generic_pwm is
    generic(
        GENERIC_PWM_MAX_PERIOD_VALUE            : natural := 9;        -- max counter value as natural
        GENERIC_PWM_MAX_CNT_BITS                : natural := 4;         -- max counter value in bits, can be different than CNT_MAX_VALUE
        GENERIC_PWM_DUTY_SIGNAL_START_LEVEL     : std_logic := '1' 
    );
    port (
        clk_i            : in std_logic;
        reset_n_i        : in std_logic; 
        pwm_duty_value_i : in std_logic_vector (GENERIC_PWM_MAX_CNT_BITS - 1 downto 0);
        pwm_o            : out std_logic
    );
end;

architecture generic_pwm_behaviour of generic_pwm is

    signal pwm_sig                                : std_logic;
    signal generic_counter_value_sig              : std_logic_vector(GENERIC_PWM_MAX_CNT_BITS - 1 downto 0);

begin

    generic_counter_0 : entity base_component_lib.generic_counter 
    generic map(
        GENERIC_COUNTER_MIN_CNT_VALUE => 0,
        GENERIC_COUNTER_MAX_CNT_VALUE => GENERIC_PWM_MAX_PERIOD_VALUE,
        GENERIC_COUNTER_MAX_BITS => GENERIC_PWM_MAX_CNT_BITS
    )
    port map(
        clk_i => clk_i,
        reset_n_i => reset_n_i,
        generic_counter_value_o => generic_counter_value_sig
    );


    logic : process (generic_counter_value_sig, pwm_duty_value_i)
    begin
    
        if (generic_counter_value_sig < pwm_duty_value_i) then  
            pwm_sig <= GENERIC_PWM_DUTY_SIGNAL_START_LEVEL;
        else
            pwm_sig <= not GENERIC_PWM_DUTY_SIGNAL_START_LEVEL;
        end if;

    end process logic;


    ff : process(clk_i, reset_n_i)
    begin

        if reset_n_i = '0' then
            -- reset pwm signal
            pwm_o <= not GENERIC_PWM_DUTY_SIGNAL_START_LEVEL;
        elsif (clk_i'event and clk_i = '1') then
            pwm_o <= pwm_sig;
        end if;

    end process ff;

end architecture generic_pwm_behaviour;
