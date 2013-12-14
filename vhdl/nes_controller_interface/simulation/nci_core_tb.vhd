library ieee;
  use ieee.std_logic_1164.all;
  use ieee.std_logic_arith.all;

library work;
  use work.nci_pkg.all;

entity nci_core_tb is
end;

architecture nci_core_tb_behaviour of nci_core_tb is

  constant CLK_P : time := 20 ns; --50 MHz
  constant CLK_HALF_P : time := 10 ns;

  constant LATCH_TIME : time := 12 us;
  constant CONTROLLER_SHIFT_TIME : time := 12 us;
  constant SIGNAL_HOLD_TIME   : time := 11892 us; --(12000-(9*12)) us;


component nci_core is
  port (
      reset_n_i             : in std_logic;
      clk50m_i              : in std_logic;
      nci_in_controller_i   : in type_nci_in_controller;

      nci_out_controller_o  : out type_nci_out_controller;
      nci_user_o            : out type_nci_out_user;
      feel_clk_o            : out std_logic
  );
end component;

  signal reset_sig                  : std_logic; --low active!
  signal clk_sig                    : std_logic;
  signal nci_in_controller_i_sig    : type_nci_in_controller;
  signal nci_out_controller_o_sig   : type_nci_out_controller;
  signal nci_user_o_sig             : type_nci_out_user;

begin

  nci_core_0 : nci_core port map(
    reset_n_i => reset_sig,
    clk50m_i => clk_sig,
    nci_in_controller_i => nci_in_controller_i_sig,
    nci_out_controller_o => nci_out_controller_o_sig,
    nci_user_o => nci_user_o_sig,
    feel_clk_o => open
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

    nci_in_controller_i_sig.data_n <= '1';

    -- do reset
    reset_sig <= '0';
    wait for 10 * CLK_P;
    reset_sig <= '1';
    wait for 10 * CLK_P;

    -- NOTE: nci_in_controller_i_sig.data is active low!

    -- button a
    wait for LATCH_TIME;
    nci_in_controller_i_sig.data_n <= '0';
    wait for CONTROLLER_SHIFT_TIME;
    nci_in_controller_i_sig.data_n <= '1';
    wait for 7 * CONTROLLER_SHIFT_TIME;
    wait for SIGNAL_HOLD_TIME;

    -- button a
    wait for LATCH_TIME;
    nci_in_controller_i_sig.data_n <= '0';
    wait for CONTROLLER_SHIFT_TIME;
    nci_in_controller_i_sig.data_n <= '1';
    wait for 7 * CONTROLLER_SHIFT_TIME;
    wait for SIGNAL_HOLD_TIME;

    -- button b
    wait for LATCH_TIME;
    nci_in_controller_i_sig.data_n <= '1';
    wait for CONTROLLER_SHIFT_TIME;
    nci_in_controller_i_sig.data_n <= '0';
    wait for CONTROLLER_SHIFT_TIME;
    nci_in_controller_i_sig.data_n <= '1';
    wait for 6 * CONTROLLER_SHIFT_TIME;
    wait for SIGNAL_HOLD_TIME;

    -- button a
    wait for LATCH_TIME;
    nci_in_controller_i_sig.data_n <= '0';
    wait for CONTROLLER_SHIFT_TIME;
    nci_in_controller_i_sig.data_n <= '1';
    wait for 7 * CONTROLLER_SHIFT_TIME;
    wait for SIGNAL_HOLD_TIME;

    -- button select
    wait for LATCH_TIME;
    nci_in_controller_i_sig.data_n <= '1';
    wait for 2 * CONTROLLER_SHIFT_TIME;
    nci_in_controller_i_sig.data_n <= '0';
    wait for CONTROLLER_SHIFT_TIME;
    nci_in_controller_i_sig.data_n <= '1';
    wait for 5 * CONTROLLER_SHIFT_TIME;
    wait for SIGNAL_HOLD_TIME;

    -- button start
    wait for LATCH_TIME;
    nci_in_controller_i_sig.data_n <= '1';
    wait for 3 * CONTROLLER_SHIFT_TIME;
    nci_in_controller_i_sig.data_n <= '0';
    wait for CONTROLLER_SHIFT_TIME;
    nci_in_controller_i_sig.data_n <= '1';
    wait for 4 * CONTROLLER_SHIFT_TIME;
    wait for SIGNAL_HOLD_TIME;

    nci_in_controller_i_sig.data_n <= '1';

    wait;

  end process stimulus;

end architecture nci_core_tb_behaviour;