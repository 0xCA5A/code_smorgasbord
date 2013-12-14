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

