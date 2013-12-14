onerror {resume}
quietly WaveActivateNextPane {} 0
add wave -noupdate -expand -group component
add wave -noupdate -group component -format Logic /generic_pwm_tb/generic_pwm_0/clk_i
add wave -noupdate -group component -format Logic -radix hexadecimal /generic_pwm_tb/generic_pwm_0/reset_n_i
add wave -noupdate -group component -format Literal -radix hexadecimal /generic_pwm_tb/generic_pwm_0/pwm_duty_value_i
add wave -noupdate -group component -format Logic -radix hexadecimal /generic_pwm_tb/generic_pwm_0/pwm_o
add wave -noupdate -group component -format Logic -radix hexadecimal /generic_pwm_tb/generic_pwm_0/pwm_sig
add wave -noupdate -group component -format Literal -radix hexadecimal /generic_pwm_tb/generic_pwm_0/generic_counter_value_sig
add wave -noupdate -group component -format Logic -radix hexadecimal /generic_pwm_tb/generic_pwm_0/clk_i
add wave -noupdate -group component -format Logic -radix hexadecimal /generic_pwm_tb/generic_pwm_0/reset_n_i
add wave -noupdate -group component -format Literal -radix hexadecimal /generic_pwm_tb/generic_pwm_0/pwm_duty_value_i
add wave -noupdate -group component -format Logic -radix hexadecimal /generic_pwm_tb/generic_pwm_0/pwm_o
add wave -noupdate -group component -format Logic -radix hexadecimal /generic_pwm_tb/generic_pwm_0/pwm_sig
add wave -noupdate -group component -format Literal -radix hexadecimal /generic_pwm_tb/generic_pwm_0/generic_counter_value_sig
add wave -noupdate -expand -group tb
add wave -noupdate -group tb -format Logic -radix hexadecimal /generic_pwm_tb/clk_sig
add wave -noupdate -group tb -format Logic -radix hexadecimal /generic_pwm_tb/reset_n_sig
add wave -noupdate -group tb -format Literal -radix hexadecimal /generic_pwm_tb/pwm_duty_sig
add wave -noupdate -group tb -format Logic -radix hexadecimal /generic_pwm_tb/pwm_sig
TreeUpdate [SetDefaultTree]
WaveRestoreCursors {{Cursor 1} {49997 ns} 0} {{Cursor 2} {880 ns} 0}
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
update
WaveRestoreZoom {395 ns} {729 ns}
