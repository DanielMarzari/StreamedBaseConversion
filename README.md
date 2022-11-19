# StreamedBaseConversion

***HOW IT WORKS***
Horner's Rule (least->most significant place / little-endian byte order)
<img src="http://www.sciweavers.org/tex2img.php?eq=a_0%20%2B%20a_1x%20%2B%20a_2x%5E2%20%2B%20...%20a_nx%5En%20%3D%20a_0%20%2B%20x%28a_1%20%2B%20x%28a_2%20%2B%20...%20x%28a_%7Bn-1%7D%20%2B%20a_nx%5En%29%29%29&bc=White&fc=Black&im=jpg&fs=12&ff=arev&edit=0" align="center" border="0" alt="a_0 + a_1x + a_2x^2 + ... a_nx^n = a_0 + x(a_1 + x(a_2 + ... x(a_{n-1} + a_nx^n)))" width="540" height="21" />

Reversed Horner's Rule (most->least significant place / big-endian byte order)
<img src="http://www.sciweavers.org/tex2img.php?eq=a_nx%5En%20%2B%20...%20%20a_2x%5E2%20%2B%20a_1x%20%2B%20a_0%20%3D%20%28%28%28a_nx%20%2B%20a_%7Bn-1%7D%29x%20%2B%20...%20a_2%29x%20%2B%20a_1%29x%20%2B%20a0&bc=White&fc=Black&im=jpg&fs=12&ff=arev&edit=0" align="center" border="0" alt="a_nx^n + ...  a_2x^2 + a_1x + a_0 = (((a_nx + a_{n-1})x + ... a_2)x + a_1)x + a0" width="533" height="21" />

See https://en.wikipedia.org/wiki/Horner%27s_method for more

***OPTIMIZATIONS***
In order to handle extremely large files, this project reads files using BitStream (increments of 1 byte) and assumes the input base to be 2 (binary data).
