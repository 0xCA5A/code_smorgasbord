-------------------------------------------------------
--! @file generic_counter.vhd
--! @brief generic two process counter, counts from generic min value to generic max value
-------------------------------------------------------

library ieee;
    use ieee.std_logic_1164.all;
    use ieee.numeric_std.all;

library base_component_lib;
    use base_component_lib.base_component_pkg.all;


entity generic_counter is
    generic(
        GENERIC_COUNTER_MIN_CNT_VALUE   : natural := 0;         -- SUBTYPE natural IS integer RANGE 0 TO 2_147_483_647;
        GENERIC_COUNTER_MAX_CNT_VALUE   : natural := 9;         -- max counter value as integer
        GENERIC_COUNTER_MAX_BITS        : natural := 4          -- max counter value in bits, can be different than CNT_MAX_VALUE
    );
    port (
        clk_i                           : in std_logic;
        reset_n_i                       : in std_logic;         -- low active!
        generic_counter_value_o         : out std_logic_vector(GENERIC_COUNTER_MAX_BITS - 1 downto 0)
    );
end;


architecture generic_counter of generic_counter is
    
    -- signal definition
    signal cnt_next_value_sig              : natural := GENERIC_COUNTER_MIN_CNT_VALUE;
    signal cnt_act_value_sig               : natural := GENERIC_COUNTER_MIN_CNT_VALUE;

begin

--    -- configuration check process    
--    config_check : process
--    begin
--
--        assert GENERIC_COUNTER_MAX_CNT_VALUE < "**"(2, GENERIC_COUNTER_MAX_BITS) 
--        report "bad generic counter configuration, check max counter value and nr of counter bits" 
--        severity failure;
--        
--        assert GENERIC_COUNTER_MIN_CNT_VALUE < GENERIC_COUNTER_MAX_CNT_VALUE 
--        report "bad generic counter configuration, check min and max counter values." 
--        severity failure;
--
--        wait;
--
--    end process config_check;

    -- logic process
    logic : process (cnt_act_value_sig)
    begin

        if cnt_act_value_sig >= GENERIC_COUNTER_MAX_CNT_VALUE then
            -- reset counter
            cnt_next_value_sig <= GENERIC_COUNTER_MIN_CNT_VALUE;
        else
            cnt_next_value_sig <= cnt_act_value_sig + 1;
        end if;

    end process logic;

    -- ff process
    ff : process(clk_i, reset_n_i)
    begin
    
        if reset_n_i = '0' then
            -- reset counter value
            cnt_act_value_sig <= GENERIC_COUNTER_MIN_CNT_VALUE;
        elsif clk_i'event and clk_i = '1' then
            -- store counter value
            cnt_act_value_sig <= cnt_next_value_sig;
        end if;

    end process ff;

    -- signal assignment
    generic_counter_value_o <= std_logic_vector(to_unsigned(cnt_act_value_sig, GENERIC_COUNTER_MAX_BITS));

end architecture generic_counter;
