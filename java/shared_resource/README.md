## Purpose
Application should generate a lot of objects, GC should clean up.
Use jconsole to monitor what the JVM is doing exactly.

## Monitoring
Enable JMX / RMI and run application:
```
sam@host$ ant monitor
```

```
sam@host$ jconsole
```
Source: JDK
Howto jconsole: https://docs.oracle.com/javase/7/docs/technotes/guides/management/jconsole.html

```
sam@host$ visualvm
```
Source: https://visualvm.github.io/
