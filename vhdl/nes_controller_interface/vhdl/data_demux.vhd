--------------------------------------------------------------------------
--------------------------------------------------------------------------
-- $LastChangedDate: 2009-03-24 19:43:25 +0100 (Tue, 24 Mar 2009) $
-- $Rev: 116 $
-- $Author: cass $
-- $URL: https://rom.zhaw.ch/svn/kickstart/my_projects/vhdl/nes_controller_interface/trunk/vhdl/data_demux.vhd $
-- $Id: data_demux.vhd 116 2009-03-24 18:43:25Z cass $
--------------------------------------------------------------------------
--------------------------------------------------------------------------

-------------------------------------------------------
--! @file latch_driver.vhd
--! @brief latch driver
-------------------------------------------------------

--! Use standard library
library ieee;
--! Use logic elements
    use ieee.std_logic_1164.all;

library work;
    use work.nci_pkg.all;

entity data_demux is
  port (
    reset_n_i             : in std_logic; --low active!
    controller_clk_i      : in std_logic;
    nci_in_controller_i   : in type_nci_in_controller;
    nci_out_user_o        : out type_nci_out_user
  );
end entity data_demux;

architecture data_demux_behavior of data_demux is
  signal controller_clk_falling_edge_counter_value : natural := 0;
  signal nci_out_user_o_sig : type_nci_out_user;
begin


  feel_proc : process (reset_n_i, controller_clk_i)
  begin

    if (reset_n_i = '0') then
      controller_clk_falling_edge_counter_value <= 0;
      nci_out_user_o_sig <= (others => '0');
    elsif ((controller_clk_i = '0' and controller_clk_i'event)) then

      case controller_clk_falling_edge_counter_value is
        when 0 => nci_out_user_o_sig.a <= not nci_in_controller_i.data_n;
        when 1 => nci_out_user_o_sig.b <= not nci_in_controller_i.data_n;
        when 2 => nci_out_user_o_sig.sel <= not nci_in_controller_i.data_n;
        when 3 => nci_out_user_o_sig.start <= not nci_in_controller_i.data_n;
        when 4 => nci_out_user_o_sig.up <= not nci_in_controller_i.data_n;
        when 5 => nci_out_user_o_sig.down <= not nci_in_controller_i.data_n;
        when 6 => nci_out_user_o_sig.left <= not nci_in_controller_i.data_n;
        when 7 => nci_out_user_o_sig.right <= not nci_in_controller_i.data_n;
        when others => nci_out_user_o_sig <= (others => '0');
      end case;

      if (controller_clk_falling_edge_counter_value < 7) then
        controller_clk_falling_edge_counter_value <= controller_clk_falling_edge_counter_value + 1;
      else
        controller_clk_falling_edge_counter_value <= 0;
      end if;

    end if;

  end process feel_proc;

  nci_out_user_o <= nci_out_user_o_sig;

end architecture data_demux_behavior;