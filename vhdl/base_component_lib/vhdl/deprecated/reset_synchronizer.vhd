-------------------------------------------------------
--! @file reset_synchronizer.vhd
--! @brief reset synchronizer to 
-------------------------------------------------------

library ieee;
  use ieee.std_logic_1164.all;

library work;
  use work.base_component_pkg.all;


--! reset syncronizer interface description
entity reset_synchronizer is
  port (

    slowest_system_clk_i          : in std_logic; --take the slowest system clock to drive the reset logic
    user_reset_n_i                : in std_logic; --low active!

    sync_reset_falling_n_o        : out std_logic

  );
end;

--! @brief architure definition of the reset syncronizer
--! @details incoming user reset signal will be held low for a minimal defined duration
architecture reset_synchronizer_behaviour of reset_synchronizer is
  signal user_reset_signal_sync_n                  : std_logic;
  signal user_reset_signal_sync_delay_n            : std_logic;
  signal counter_value_sig                         : type_generic_counter_out; 
begin


  -- components
  signal_synchronizer_falling_0 : signal_synchronizer_falling
  port map(
    clk_i => slowest_system_clk_i,
    reset_n_i => open, -- nothing gets attached here
    signal_async_i => user_reset_n_i,
    signal_sync_o => user_reset_signal_sync_n
  );

  generic_counter_0 : generic_counter generic map(
    CNT_START_VALUE => 1,
    CNT_MAX_VALUE => RESET_SYNCHRONIZER_MIN_RESET_CLKS
  )
  port map(
    clk_i => slowest_system_clk_i,
    reset_n => open,
    counter_out => counter_value_sig
  );


  g1 : for i in 0 to 9 generate
    fm : signal_synchronizer_rising
          port map(
            clk_i => slowest_system_clk_i,
            reset_n_i => open, -- nothing gets attached here
            signal_async_i => user_reset_n_i,
            signal_sync_o => user_reset_signal_sync_n
          );
  end generate g1;


--   signal_synchronizer_falling_0 : signal_synchronizer_falling
--   port map(
--     slowest_system_clk_i,
--     open,
--     user_reset_n_i,
--     user_reset_signal_sync_n
--   );




--   sync_proc : process(reset_n_i, signal_async_i, clk_i)
--   begin
--     if (reset_n_i = '0') then
--       ff_signal <= '0';
--     elsif (clk_i'event and clk_i = '1') then
--       ff_signal <= signal_async_i;
--     end if;
--   end process sync_proc;
-- 
--   out_proc : process(reset_n_i, clk_i, ff_signal)
--   begin
--     if (reset_n_i = '0') then
--       signal_sync_o <= '0';
--     elsif (clk_i'event and clk_i = '1') then
--       signal_sync_o <= ff_signal;
--     end if;
--   end process out_proc;

end architecture reset_synchronizer_behaviour;






-- entity signal_synchronizer_rising is port (
--           clk, adata: in std_logic;
--           synched : out std_logic);
-- end entity signal_synchronizer_rising;
-- 
-- architecture procedural of signal_synchronizer_rising is
--   type state is (s0, s1);
--   signal current : state;
-- begin
-- 
-- process (clk) begin
-- 
--   if (rising_edge(clk)) then
--     if current = s0 then
--       if adata = '0' then
--         current <= s0;
--       else
--         current <= s1;
--       end if;
--       else --current = s1
--         current <= s0;
--       end if;
--     end if;
-- 
-- 
-- 
-- 
-- end process;
-- 
-- 
--   synched <= '1' when current = s1 else '0';
-- 
-- end architecture procedural;