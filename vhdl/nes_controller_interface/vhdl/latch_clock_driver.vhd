--------------------------------------------------------------------------
--------------------------------------------------------------------------
-- $LastChangedDate: 2009-03-24 19:43:25 +0100 (Tue, 24 Mar 2009) $
-- $Rev: 116 $
-- $Author: cass $
-- $URL: https://rom.zhaw.ch/svn/kickstart/my_projects/vhdl/nes_controller_interface/trunk/vhdl/latch_clock_driver.vhd $
-- $Id: latch_clock_driver.vhd 116 2009-03-24 18:43:25Z cass $
--------------------------------------------------------------------------
--------------------------------------------------------------------------

-------------------------------------------------------
--! @file latch_clock_driver.vhd
--! @brief latch and controller clock signal driver
-------------------------------------------------------

--! Use standard library
library ieee;
--! Use logic elements
    use ieee.std_logic_1164.all;

entity latch_clock_driver is
  generic(
    LATCHPULSE_START_VALUE                  : natural := 0; --SUBTYPE natural IS integer RANGE 0 TO 2_147_483_647;
    LATCHPULSE_STOP_VALUE                   : natural := 0;
    HALF_FEEL_CLK_COUNT_VALUE               : natural := 0;
    NR_OF_CONTROLLER_CLK_PULSE              : natural := 0
  );
  port (
    clk50m_i                                : in std_logic;
    reset_n_i                               : in std_logic; --low active!
    latch_pulse_o                           : out std_logic;
    feel_clk_o                              : out std_logic;
    controller_clk_o                        : out std_logic
  );
end entity latch_clock_driver;

-- reset nur auf den logikprozess
architecture latch_clock_driver_behavior of latch_clock_driver is

  signal cnt_act_feel_value_sig             : natural := 0;
  signal cnt_act_controller_clk_value_sig   : natural := 0;
  signal latch_level_sig                    : std_logic := '0';
  signal feel_clk_overflow_level_sig        : std_logic := '0';
  signal controller_clk_level_sig           : std_logic := '0';

begin

  -- logic
  counter_proc : process (reset_n_i, clk50m_i)
  begin

    if (reset_n_i = '0') then
      latch_level_sig <= '0';
      feel_clk_overflow_level_sig <= '0';
      controller_clk_level_sig <= '0';
      cnt_act_controller_clk_value_sig <= 0;

    -- rising clk edge
    elsif (clk50m_i = '1' and clk50m_i'event) then

      --reset signals
      controller_clk_level_sig <= '0';
      latch_level_sig <= '0';
      feel_clk_overflow_level_sig <= '0';

      -- check for overflow
      if (cnt_act_feel_value_sig < ((HALF_FEEL_CLK_COUNT_VALUE * 2)-1)) then

        -- drive latch signal
        if ((cnt_act_feel_value_sig >= LATCHPULSE_START_VALUE) AND (cnt_act_feel_value_sig <= (LATCHPULSE_STOP_VALUE))) then
          latch_level_sig <= '1';
        end if;

        -- drive controller clock signal
        if ((cnt_act_feel_value_sig > (LATCHPULSE_STOP_VALUE)) AND (cnt_act_feel_value_sig < ((1+NR_OF_CONTROLLER_CLK_PULSE)*600))) then

          -- generate controller clk

          -- NOTE: VERY BAD STUFF! NEEDS INF. LOGIC CELLS!
          --if(cnt_act_value_sig MOD 600 < 300) then

          if (cnt_act_controller_clk_value_sig < 300) then
            controller_clk_level_sig <= '1';
          end if;

          -- increment controller clock counter
          cnt_act_controller_clk_value_sig <= cnt_act_controller_clk_value_sig + 1;

          -- reset controller clock counter
          if (cnt_act_controller_clk_value_sig >= 600-1) then
            cnt_act_controller_clk_value_sig <= 0;
          end if;

        end if;

        -- drive feel clock signal
        if (cnt_act_feel_value_sig < HALF_FEEL_CLK_COUNT_VALUE) then
          feel_clk_overflow_level_sig <= '1';
        end if;

        cnt_act_feel_value_sig <= cnt_act_feel_value_sig + 1;

      else
        cnt_act_feel_value_sig <= 0;
      end if;


    end if;

  end process counter_proc;

  -- assign signals
  latch_pulse_o <= latch_level_sig;
  feel_clk_o <= feel_clk_overflow_level_sig;
  controller_clk_o <= controller_clk_level_sig;

end architecture latch_clock_driver_behavior;