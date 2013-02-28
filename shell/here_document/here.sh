#!/bin/bash

echo "hello from $0"

cat > hello.sh << 'EOF'
#!/bin/bash
echo "hello from $0"
EOF

chmod +x hello.sh
cat hello.sh
./hello.sh

cat > hello.c << 'EOF'
#include <stdio.h>
int main(void)
{
    printf("hello from c\n");
    return 0;
}
EOF

make hello
./hello

echo "done!"