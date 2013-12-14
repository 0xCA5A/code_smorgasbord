--------------------------------------------------------------------------
--------------------------------------------------------------------------
-- $LastChangedDate: 2009-12-28 10:42:29 +0100 (Mon, 28 Dec 2009) $
-- $Rev: 202 $
-- $Author: cass $
-- $URL: https://rom.zhaw.ch/svn/kickstart/my_projects/vhdl/base_component_lib/trunk/vhdl/deprecated/signal_synchronizer_falling.vhd $
-- $Id: signal_synchronizer_falling.vhd 202 2009-12-28 09:42:29Z cass $
--------------------------------------------------------------------------
--------------------------------------------------------------------------

-------------------------------------------------------
--! @file signal_synchronizer_falling.vhd
--! @brief signal synchronizer (falling edge)
-------------------------------------------------------

library ieee;
  use ieee.std_logic_1164.all;

-- library work;
--    use work.base_component_pkg.all;


--! signal syncronizer (falling edge) interface description
entity signal_synchronizer_falling is
  port (

    clk_i            : in std_logic;
    reset_n_i        : in std_logic; --low active!
    signal_async_i   : in std_logic;

    signal_sync_o    : out std_logic

  );
end;



--! @brief architure definition of the signal syncronizer (falling edge)
--! @details incoming signal will be synchronized through two flipflop's
architecture signal_synchronizer_falling_behaviour of signal_synchronizer_falling is
  signal ff_signal                         : std_logic;
begin

  sync_proc : process(reset_n_i, signal_async_i, clk_i)
  begin
    if (reset_n_i = '0') then
      ff_signal <= '0';
    elsif (clk_i'event and clk_i = '1') then
      ff_signal <= signal_async_i;
    end if;
  end process sync_proc;

  out_proc : process(reset_n_i, clk_i, ff_signal)
  begin
    if (reset_n_i = '0') then
      signal_sync_o <= '0';
    elsif (clk_i'event and clk_i = '1') then
      signal_sync_o <= ff_signal;
    end if;
  end process out_proc;

end architecture signal_synchronizer_falling_behaviour;

