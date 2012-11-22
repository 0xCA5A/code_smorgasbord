
CFLAGS = -O2 -g -Werror
# CFLAGS = -funit-at-a-time -O1 -g -Werror

OBJ = std_stuff_module.o inline_stuff_module.o main.o
ASM = std_stuff_module.asm inline_stuff_module.asm main.asm
APP = application




%.o: %.c
	@echo "# $@ (pattern)"
	$(CC) $(CFLAGS) -c $^ -o $@
	
%.asm: %.c
	@echo "# $@ (pattern)"
	$(CC) $(CFLAGS) -c -S $^ -o $@



build: $(OBJ)
	@echo "# $@ (target)"
	$(CC) $(CFLAGS) $(OBJ) -o $(APP)

asm: $(ASM)
	@echo "# $@ (target)"

clean:
	@echo "# $@ (target)"
	rm -fv *.o
	rm -fv *.asm
	rm -fv $(APP)
