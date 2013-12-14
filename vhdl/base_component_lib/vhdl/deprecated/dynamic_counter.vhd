-------------------------------------------------------
--! @file dynamic_counter.vhd
--! @brief dynamic counter
-------------------------------------------------------

library ieee;
  use ieee.std_logic_1164.all;
  use ieee.numeric_std.all;

library work;
  use work.base_component_pkg.all;

entity dynamic_counter is
  generic(
    CNT_START_VALUE         : natural := 0; --SUBTYPE natural IS integer RANGE 0 TO 2_147_483_647;
    CNT_MAX_VALUE           : natural := DYNAMIC_COUNTER_MAX_CNT_VALUE
  );
  port (
    clk_i                   : in std_logic;
    reset_n_i               : in std_logic; -- low active!
    reset_value_i           : in natural;   -- 100 means count from 0 to 100 -> 100 x t_clk, rising edge!
    counter_value_o         : out natural
  );
end;

architecture dynamic_counter_behaviour of dynamic_counter is

  signal cnt_next_value_sig    : natural := CNT_START_VALUE;
  signal cnt_act_value_sig     : natural := CNT_START_VALUE;

begin

  dc_logic : process (cnt_act_value_sig, cnt_next_value_sig, reset_n_i, reset_value_i)
  begin

    if reset_n_i = '0' then                         --check asynchron reset sinal
      cnt_next_value_sig <= CNT_START_VALUE;
    elsif cnt_act_value_sig >= reset_value_i then   --check reset value, reset counter
      cnt_next_value_sig <= CNT_START_VALUE;
    elsif cnt_act_value_sig >= CNT_MAX_VALUE then   --check max value, reset counter
      cnt_next_value_sig <= CNT_START_VALUE;
    else
      cnt_next_value_sig <= cnt_act_value_sig + 1;  -- count++
    end if;

  end process dc_logic;


  dc_ff : PROCESS(clk_i, cnt_next_value_sig)
  begin

    if clk_i'event and clk_i = '1' then
      counter_value_o <= cnt_next_value_sig;
      cnt_act_value_sig <= cnt_next_value_sig;  
    end if;

  end process dc_ff;

end architecture dynamic_counter_behaviour;
