# Minerva_RAM_Test
Revision of the RAM Test Utility for QL to detect faulty RAM ICs.

The HTML&PHP revision 0.2 converted by Xad/Nightfall, relies on the codes that Minerva or the basic program developed by Dominic Brown. It in a smart way check if an IC is faulty (must be a compleately faulty IC) and gives 3 codes to the user (Write, Read and the Address). 

With the 3 codes the user must introduce them into the PHP mentioned tool aviable at http://www.nightfallcrew.com/minervaram . Sadly it was only useful for a part of users that have a QL with stock RAM of 128K.

So due to the mentioned aboved, here is a remake of the code that allows to other users with some mods that expand the original memory placed on the ICs sockets/places till 512K (Danish mod) or 640K to get the right IC that is faulty.  


I has been tested with some original warning Minerva codes from 128K QLs and also with QL with Danish mod of 512K.

Not tested yet with mods of 640K (usually this mod has an extra IC in piggybag over original RAM ICs), but should work the same

Next part of the work will face the basic program in order to give to it more functionalities, but by now it is more than enough.

This document in still under development same as the code getting more features.

Not full tested yet, so use it under your own risk.

![My image](images/GUI_1.png) 
![My image](images/GUI_2.png) 

Credits:
The Original Program (ram3_ramfail.bas) was written for the SINCLAIR QL in SUPER BASIC language by Dominic Brown (1990).
Converted in PHP language by Xad/Nightfall (8/5/2015).
