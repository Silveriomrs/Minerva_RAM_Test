<?php
// Xad/Nightfall (8/5/2015)
// Modified by Popopo (09/04/2025)

// Write below the output of the Minerva ROM Memory check:
// Faulty Ram Decoder v0.3 Online: http://www.nightfallcrew.com/minervaram

$write = "548C4878";
$read  = "5CCD5CCD";
$address = "00032000";
$results = 0;
$ix = 0;

// -----------------------------------------

echo "\nMinerva (Sinclair QL) Faulty Ram Decoder v0.3\n\n";

echo "- MINERVA OUTPUT SCREEN -\n";
echo "-------------------------\n";
echo "WRITE:    ".implode(' ',str_split($write))."\n";
echo "READ:     ".implode(' ',str_split($read))."\n";
echo "ADDRESS:  ".implode(' ',str_split($address))."\n";
echo "-------------------------\n";

echo "\n";

if ($address >= 40000)
{
	echo "Faulty ram is in expansion memory\n";
	exit;
}

for ($i = 0; $i <= 6; $i += 2)
{
    $results = $results|hexdec(substr($write, $i, 2))^hexdec(substr($read, $i, 2));
}
    $resultsToBinary = substr("00000000",0,8 - strlen(decbin($results))).decbin($results);

if ($address >= 30000)
{
    echo "ADDRESS > 30000\n";
    echo "BINARY STRING: $resultsToBinary\n\n";

    $ram = array("IC16","IC15","IC14","IC13","IC12","IC11","IC10","IC9");
    foreach($ram as $value)
    {
        if (substr($resultsToBinary, $ix, 1) == 1)
        {
            echo $value." (BAD)\n";
        } else {
            echo $value." (GOOD)\n";
        }
    $ix++;
    }
    echo "\n";

} else {

    echo "ADDRESS < 30000\n";
    echo "BINARY STRING: $resultsToBinary\n";
    echo "\n";

    $ram = array("IC8","IC7","IC6","IC5","IC4","IC3","IC2","IC1");
    foreach($ram as $value)
    {
        if (substr($resultsToBinary, $ix, 1) == 1)
        {
            echo $value." (BAD)\n";
        } else {
            echo $value." (GOOD)\n";
        }
    $ix++;
    }
    echo "\n";
}

echo "The Original Program (ram3_ramfail.bas) was written for the SINCLAIR QL in SUPER BASIC language by Dominic Brown (1990).\n";
echo "Converted in PHP language by Xad/Nightfall (8/5/2015).\n\n";

/* Output Screen:

Minerva (Sinclair QL) Ram Fail Decoder v0.2

- MINERVA OUTPUT SCREEN -
-------------------------
WRITE:    5 4 8 C 4 8 7 8
READ:     5 C C D 5 C C D
ADDRESS:  0 0 0 3 2 0 0 0
-------------------------

ADDRESS > 30000
BINARY STRING: 11111101

IC16 (BAD)
IC15 (BAD)
IC14 (BAD)
IC13 (BAD)
IC12 (BAD)
IC11 (BAD)
IC10 (GOOD)
IC9 (BAD)

The Original Program (ram3_ramfail.bas) was written for the SINCLAIR QL in SUPER BASIC language by Dominic Brown (1990).
Converted in PHP language by Xad/Nightfall (8/5/2015).

*/
?>
