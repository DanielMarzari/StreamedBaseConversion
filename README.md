# StreamedBaseConversion

***HOW IT WORKS***

Horner's Rule (least->most significant place / little-endian byte order)
![Image](https://github.com/DanielMarzari/StreamedBaseConversion/blob/main/img/SBC_EQ1.png?raw=true)

Reversed Horner's Rule (most->least significant place / big-endian byte order)
![Image](https://github.com/DanielMarzari/StreamedBaseConversion/blob/main/img/SBC_EQ2.png?raw=true)

See https://en.wikipedia.org/wiki/Horner%27s_method for more

***OPTIMIZATIONS***

In order to handle extremely large files, this project reads files using BitStream (increments of 1 byte) and assumes the input base to be 2 (binary data).
