onerror {resume}
quietly WaveActivateNextPane {} 0
add wave -noupdate -format Logic -radix hexadecimal /generic_counter_tb/clk_sig
add wave -noupdate -format Logic -radix hexadecimal /generic_counter_tb/reset_n_sig
add wave -noupdate -format Literal -radix hexadecimal /generic_counter_tb/generic_counter_value_sig
TreeUpdate [SetDefaultTree]
WaveRestoreCursors {{Cursor 1} {140 ns} 0}
configure wave -namecolwidth 448
configure wave -valuecolwidth 158
configure wave -justifyvalue left
configure wave -signalnamewidth 0
configure wave -snapdistance 10
configure wave -datasetprefix 0
configure wave -rowmargin 4
configure wave -childrowmargin 2
configure wave -gridoffset 0
configure wave -gridperiod 1
configure wave -griddelta 40
configure wave -timeline 0
configure wave -timelineunits ns
update
WaveRestoreZoom {0 ns} {1050 ns}
