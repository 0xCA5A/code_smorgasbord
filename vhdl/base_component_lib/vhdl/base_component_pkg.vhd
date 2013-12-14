--------------------------------------------------------------------------
--------------------------------------------------------------------------
-- $LastChangedDate: 2009-12-28 14:10:42 +0100 (Mon, 28 Dec 2009) $
-- $Rev: 206 $
-- $Author: cass $
-- $URL: https://rom.zhaw.ch/svn/kickstart/my_projects/vhdl/base_component_lib/trunk/vhdl/base_component_pkg.vhd $
-- $Id: base_component_pkg.vhd 206 2009-12-28 13:10:42Z cass $
--------------------------------------------------------------------------
--------------------------------------------------------------------------

-------------------------------------------------------
--! @file base_component_pkg.vhd
--! @brief base component package
------------------------------------------------------


--------------------------------------------------------------------------
-- notes
--
--------------------------------------------------------------------------
-- component generic counter
--------------------------------------------------------------------------
--    counter_value range is defined from GENERIC_COUNTER_MIN_VALUE to 
--    GENERIC_COUNTER_MAX_VALUE (NATURAL'low to NATURAL'high). 
--    this is done to keep the defined record dynamic. 
--    check that the synthesizer optimizes your design!


library ieee;
    use ieee.std_logic_1164.all;


package base_component_pkg is



end package base_component_pkg;

