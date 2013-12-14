-------------------------------------------------------
--! @file generic_pulse_generator.vhd
--! @brief generic pulse generator
-------------------------------------------------------

library ieee;
  use ieee.std_logic_1164.all;
  use ieee.numeric_std.all;

library work;
  use work.base_component_pkg.all;

entity generic_pulse_generator is
  generic(
    CLK_CNT_VALUE : natural := 0 --SUBTYPE natural IS integer RANGE 0 TO 2_147_483_647;
  );                                    --                                                  /----CLK_CNT_VALUE-----/
  port (                                --                                                  \                      \
    clk_i            : in std_logic;    --                                                  /---ppc---/            \
    reset_n_i        : in std_logic;    --                                                  \_________\            \
    pos_periode_cnt  : in natural;      -- nr of clocks, leading positive signal periode   _\         \____________\_
    pulse_o          : out std_logic
  );
end generic_pulse_generator;

-- reset nur auf den logikprozess
architecture generic_pulse_generator_behaviour of generic_pulse_generator is

  signal generic_counter_out_signal       : type_generic_counter_out;
  signal pulse_signal                     : std_logic;
  signal pulse_next_signal                : std_logic;

begin

  -- components
  counter_0 : generic_counter generic map(
    CNT_START_VALUE => 1,
    CNT_MAX_VALUE => CLK_CNT_VALUE
  )
  port map(
    clk_i => clk_i,
    reset_n => reset_n_i,
    counter_out => generic_counter_out_signal
  );

  clocked : process(reset_n_i, clk_i)
  begin
    if reset_n_i = '0' then
      pulse_next_signal <= '0';
    elsif rising_edge(clk_i) then
      if generic_counter_out_signal.counter_value <= pos_periode_cnt then
        pulse_next_signal <= '1';
      else
        pulse_next_signal <= '0';
      end if;
    else
      pulse_next_signal <= pulse_next_signal;
    end if;
  end process clocked;

  pulse_o <= pulse_next_signal;


end architecture generic_pulse_generator_behaviour;
